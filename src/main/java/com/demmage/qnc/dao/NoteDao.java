package com.demmage.qnc.dao;

import com.demmage.qnc.dao.exception.DaoException;
import com.demmage.qnc.dao.exception.DaoParamException;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.entities.ConnectionProperties;
import lombok.SneakyThrows;

import java.sql.*;

public class NoteDao {

    private final Connection connection;
    private final SqlScriptService scriptService = new SqlScriptService();

    public NoteDao(ConnectionProperties properties) {
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

    public void createNewNote(Note note) {
        execute(scriptService.createNote(), false, note.getName(), note.getHash(), note.getCreated(), note.getContent());
    }

    public Note getLastNote() {
        Note note = new Note();
        try (PreparedStatement statement = connection.prepareStatement(scriptService.getLastNoteQuery())) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            note.setName(resultSet.getString("name"));
            note.setHash(resultSet.getString("hash"));
            note.setContent(resultSet.getString("content"));
            note.setCreated(resultSet.getTimestamp("created"));

        } catch (SQLException e) {
            throw new DaoException("SQL Execution Fail", e);
        }


//        ResultSet result = execute(scriptService.getLastNoteQuery(), true);
//        return assembly(result);
        return note;
    }

    private ResultSet execute(final String sql, boolean query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                setClass(params[i], i + 1, statement);
            }

            if (query) {
                return statement.executeQuery();
            } else {
                statement.execute();
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("SQL Execution Fail", e);
        }
    }

    private Note assembly(ResultSet resultSet) {
        Note note = new Note();
        try {
            resultSet.next();
            note.setName(resultSet.getString("name"));
            note.setHash(resultSet.getString("hash"));
            note.setContent(resultSet.getString("content"));
            note.setCreated(resultSet.getTimestamp("created"));
        } catch (SQLException e) {
            throw new DaoException("SQL Execution Fail", e);
        }
        return note;
    }

    @SneakyThrows
    // TODO: 28.09.2021 Remove
    private <T> PreparedStatement setClass(T t, int index, PreparedStatement statement) {
        if (t.getClass().equals(String.class)) {
            statement.setString(index, (String) t);
        } else if (t.getClass().equals(Integer.class)) {
            statement.setInt(index, (Integer) t);
        } else if (t.getClass().equals(Timestamp.class)) {
            statement.setTimestamp(index, (Timestamp) t);
        } else {
            throw new DaoParamException("Param Type Not Supported");
        }
        return statement;
    }

    private void createTables() {
        try (PreparedStatement statement = connection.prepareStatement(scriptService.createNotesTable())) {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Table Creation Fail", e);
        }
    }
}
