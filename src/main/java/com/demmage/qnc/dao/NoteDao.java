package com.demmage.qnc.dao;

import com.demmage.qnc.dao.exception.DaoException;
import com.demmage.qnc.dao.exception.DaoParamException;
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

    private ResultSet executeQuery(final String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                setClass(params[i], i + 1, statement);
            }

            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Query Execution Fail", e);
        }
    }

    @SneakyThrows
    // TODO: 28.09.2021 Remove
    private <T> PreparedStatement setClass(T t, int index, PreparedStatement statement) {
        if (t.getClass().equals(String.class)) {
            statement.setString(index, (String)t);
        } else if (t.equals(Integer.class)) {
            statement.setInt(index, (Integer)t);
        } else {
            throw new DaoParamException("Param Type Not Supported");
        }
        return statement;
    }

    private void createTables() {
        try (PreparedStatement statement = connection.prepareStatement(scriptService.getCreateNotesTableQuery())) {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Table Creation Fail", e);
        }
    }
}
