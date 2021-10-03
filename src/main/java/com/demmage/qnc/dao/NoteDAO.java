package com.demmage.qnc.dao;

import com.demmage.qnc.dao.exception.DaoException;
import com.demmage.qnc.dao.exception.DaoParamException;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionProperties;
import com.demmage.qnc.service.SQLScriptService;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

// TODO: 03.10.2021 Do i need logger here...
@Slf4j
public class NoteDAO {

    private final Connection connection;
    private final SQLScriptService scriptService = new SQLScriptService();

    public NoteDAO(ConnectionProperties properties) {
        try {
            Class.forName(properties.getDriver());
            connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword());

        } catch (ClassNotFoundException e) {
            log.error("DB Driver Load Fail", e);
            throw new DaoException("DB Driver Load Fail", e);
        } catch (SQLException e) {
            log.error("DB Fail", e);
            throw new DaoException("DB Fail", e);
        }

        createTables();
    }

    @SneakyThrows
    public void startH2Server() {
        org.h2.tools.Server.startWebServer(connection);
    }

    public void createNewNote(Note note) {
        log.debug("Creating new note");
        execute(scriptService.createNote(), false, note.getName(), note.getHash(), note.getCreated(), note.getContent());
        log.debug("Note created");
    }

    public void appendLastNote(String newContent, String newHash) {
        Note note = getLastNote();
        final String oldContent = note.getContent();

        execute(scriptService.appendLastNote(), false, oldContent + "\n" + newContent, newHash, note.getName());
    }

    public void renameLastNote(String newName) {
        Note note = getLastNote();
        final String oldName = note.getName();

        execute(scriptService.renameLastNote(), false, newName, oldName);
    }

    public Note getLastNote() {
        log.debug("Retrieving last note");
        List<Map<String, Object>> result = execute(scriptService.getLastNoteQuery(), true);
        log.debug("Last note received");
        return assemblyNotes(result).get(0);
    }

    public void deleteNote(String name) {
        execute(scriptService.deleteNote(), false, name);
    }

    public List<Note> getAllNotes() {
        List<Map<String, Object>> result = execute(scriptService.getAllNotes(), true);
        return assemblyNotes(result);
    }

    public void deleteAllNotes() {
        log.debug("Clearing notes table");
        execute(scriptService.clearNotesTable(), false);
        log.debug("Notes table cleared");
    }

    public int getAllNotesCount() {
        // TODO: 01.10.2021 SQL Query for notes count
        return getAllNotes().size();
    }

    private List<Map<String, Object>> execute(final String sql, boolean query, Object... params) {
        log.debug("Performing sql request with args: {}", params);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                setClass(params[i], i + 1, statement);
            }

            if (query) {
                @Cleanup ResultSet resultSet = statement.executeQuery();
                ResultSetMetaData resultMeta = resultSet.getMetaData();
                final int columnCount = resultMeta.getColumnCount();

                String[] columnLabels = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columnLabels[i] = resultMeta.getColumnLabel(i + 1).toLowerCase(Locale.ROOT); // TODO: 03.10.2021 Think about lower case
                }

                List<Map<String, Object>> result = new ArrayList<>();
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        map.put(columnLabels[i - 1], resultSet.getObject(i));
                    }
                    result.add(map);
                }

                log.debug("Query statement executed");
                return result;
            } else {
                statement.execute();
                log.debug("Non-Query statement executed");
                return null; //NOSONAR
            }
        } catch (SQLException e) {
            log.error("SQL Execution Fail", e);
            throw new DaoException("SQL Execution Fail", e);
        }
    }

    private List<Note> assemblyNotes(List<Map<String, Object>> raw) {
        List<Note> notes = new ArrayList<>();
        try {
            for (Map<String, Object> m : raw) {
                Note note = new Note();

                note.setName((String) m.get("name"));
                note.setHash((String) m.get("hash"));
                note.setContent((String) m.get("content"));
                note.setCreated((Timestamp) m.get("created"));

                notes.add(note);
            }
        } catch (NullPointerException e) {
            throw new DaoException("Query result was empty", e);
        }
        return notes;
    }

    @SneakyThrows
    private void setClass(Object o, int index, PreparedStatement statement) {
        final String className = o.getClass().getSimpleName();

        Method method = Arrays.stream(statement.getClass().getMethods())
                .filter(m -> m.getName().startsWith("set") && m.getName().endsWith(className))
                .filter(m -> m.getParameterCount() == 2)
                .findFirst().orElseThrow(() -> new DaoParamException("Query Param Type Not Supported"));

        method.invoke(statement, index, o);
    }

    private void createTables() {
        log.debug("Creating table");
        execute(scriptService.createNotesTable(), false);
        log.debug("Table created");
    }
}