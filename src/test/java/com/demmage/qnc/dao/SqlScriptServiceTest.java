package com.demmage.qnc.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SqlScriptServiceTest {

    private final SqlScriptService service = new SqlScriptService();

    @Test
    public void shouldReturnSqlTableCreationQuery() {
        final String expected = "CREATE TABLE IF NOT EXISTS notes\n" +
                "(\n" +
                "    id      int primary key,\n" +
                "    name    varchar(255) unique,\n" +
                "    hash    varchar(255),\n" +
                "    content varchar(9999)\n" +
                ");";
        final String actual = service.createNotesTable();

        Assertions.assertEquals(expected, actual);
    }
}