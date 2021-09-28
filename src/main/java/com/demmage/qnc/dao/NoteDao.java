package com.demmage.qnc.dao;

import com.demmage.qnc.dao.exception.DaoException;
import com.demmage.qnc.dao.exception.DaoParamException;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.entities.ConnectionProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NoteDao {

    private final Connection connection;
    private final SqlScriptService scriptService = new SqlScriptService();

    public NoteDao(ConnectionProperties properties) {
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

    public void createNewNote(Note note) {
        log.info("Creating new note");
        execute(scriptService.createNote(), false, note.getName(), note.getHash(), note.getCreated(), note.getContent());
        log.info("Note created");
    }

    public Note getLastNote() {
        log.info("Retrieving last note");
        ResultSet result = execute(scriptService.getLastNoteQuery(), true);
        log.info("Last note received");
        return assembly(result).get(0);
    }

    public List<Note> getAllNotes() {
        return assembly(execute(scriptService.getAllNotes(), true));
    }

    public void clearNotes() {
        execute(scriptService.clearNotesTable(), false);
    }

    private ResultSet execute(final String sql, boolean query, Object... params) {
        log.debug("Executing sql script with args: {}", params);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                setClass(params[i], i + 1, statement);
            }

            if (query) {
                ResultSet resultSet = statement.executeQuery(); // Do not inline
                return resultSet;
            } else {
                statement.execute();
                log.debug("Non-Query statement executed");
                return null;
            }
        } catch (SQLException e) {
            log.error("SQL Execution Fail", e);
            throw new DaoException("SQL Execution Fail", e);
        }
    }

    private List<Note> assembly(ResultSet resultSet) {
        List<Note> notes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Note note = new Note();

                //resultSet.next();
                note.setName(resultSet.getString("name"));
                note.setHash(resultSet.getString("hash"));
                note.setContent(resultSet.getString("content"));
                note.setCreated(resultSet.getTimestamp("created"));

                notes.add(note);
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("SQL Execution Fail", e);
        }
        return notes;
    }

    @SneakyThrows
    private <T> PreparedStatement setClass(T t, int index, PreparedStatement statement) {
        if (t.getClass().equals(String.class)) {
            statement.setString(index, (String) t);
        } else if (t.getClass().equals(Integer.class)) {
            statement.setInt(index, (Integer) t);
        } else if (t.getClass().equals(Timestamp.class)) {
            statement.setTimestamp(index, (Timestamp) t);
        } else {
            log.error("Param Type Not Supported");
            throw new DaoParamException("Param Type Not Supported");
        }
        return statement;
    }

    private void createTables() {
        log.info("Creating table");
        execute(scriptService.createNotesTable(), false);
        log.info("Table created");
    }
}
