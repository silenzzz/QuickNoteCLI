package com.demmage.qnc.dao;

import com.demmage.qnc.dao.exception.DaoException;
import com.demmage.qnc.dao.exception.NoteNotFoundException;
import com.demmage.qnc.dao.exception.RequestUnsupportedParamException;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionProperties;
import com.demmage.qnc.service.SQLScriptService;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class NoteDAO {

    private final Connection connection;
    private final SQLScriptService scriptService = new SQLScriptService();

    public NoteDAO(ConnectionProperties properties) {
        try {
            Class.forName(properties.getDriver());
            connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword());

        } catch (ClassNotFoundException e) {
            throw new DaoException("DB Driver Load Fail", e);
        } catch (SQLException e) {
            throw new DaoException("DB Fail", e);
        }

        createTables();
    }

    @SneakyThrows
    public void startH2Server() {
        org.h2.tools.Server.startWebServer(connection);
    }

    public boolean createNewNote(Note note) {
        return execute(scriptService.createNote(), false, note.getName(), note.getHash(), note.getCreated(), note.getContent()).isAffected();
    }

    public boolean appendLastNote(String newContent, String newHash) {
        Note note = getLastNote();
        final String oldContent = note.getContent();

        return execute(scriptService.appendLastNote(), false, oldContent + "\n" + newContent, newHash, note.getName()).isAffected();
    }

    public boolean renameLastNote(String newName) {
        Note note = getLastNote();
        final String oldName = note.getName();

        return execute(scriptService.renameLastNote(), false, newName, oldName).isAffected();
    }

    public Note getLastNote() {
        RequestResult result = execute(scriptService.getLastNoteQuery(), true);
        return Optional.of(assemblyNotes(result.getQueryResult()).get(0)).orElseThrow(NoteNotFoundException::new);
    }

    public boolean deleteNote(String name) {
        return execute(scriptService.deleteNote(), false, name).isAffected();
    }

    public List<Note> getAllNotes() {
        RequestResult result = execute(scriptService.getAllNotes(), true);
        return assemblyNotes(result.getQueryResult());
    }

    public Note getNoteByName(String name) {
        Optional<Note> note = Optional.of(assemblyNotes(execute(scriptService.getNoteByName(), true, name).getQueryResult()).get(0));

        return note.orElseThrow(NoteNotFoundException::new);
    }

    public boolean deleteAllNotes() {
        return execute(scriptService.clearNotesTable(), false).isAffected();
    }

    public int getAllNotesCount() {
        // TODO: 01.10.2021 SQL Query for notes count
        return getAllNotes().size();
    }

    private RequestResult execute(final String sql, boolean query, Object... params) {
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
                    columnLabels[i] = resultMeta.getColumnLabel(i + 1).toLowerCase(Locale.ROOT);
                }

                List<Map<String, Object>> result = new ArrayList<>();
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        map.put(columnLabels[i - 1], resultSet.getObject(i));
                    }
                    result.add(map);
                }

                return new RequestResult(!result.isEmpty(), result);
            } else {
                statement.execute();
                return new RequestResult(statement.getUpdateCount() > 0, null);
            }
        } catch (SQLException e) {
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
                .findFirst().orElseThrow(() -> new RequestUnsupportedParamException("Query Param Type Not Supported"));

        method.invoke(statement, index, o);
    }

    private void createTables() {
        execute(scriptService.createNotesTable(), false);
    }
}