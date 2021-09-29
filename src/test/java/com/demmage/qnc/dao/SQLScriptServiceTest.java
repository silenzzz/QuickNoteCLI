package com.demmage.qnc.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLScriptServiceTest {

    private final SQLScriptService service = new SQLScriptService();

    @Test
    void shouldReturnSqlTableCreationQuery() {
        final String expected = "CREATE SEQUENCE IF NOT EXISTS counter START WITH 1 INCREMENT BY 1;\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS notes\n" +
                "(\n" +
                "    id      int primary key,\n" +
                "    name    varchar(255) unique,\n" +
                "    hash    varchar(255),\n" +
                "    created timestamp,\n" +
                "    content varchar(9999)\n" +
                ");";
        final String actual = service.createNotesTable();

        Assertions.assertEquals(expected, actual);
    }
}