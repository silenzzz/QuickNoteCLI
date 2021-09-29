package com.demmage.qnc.parser;

import com.demmage.qnc.parser.constants.SQLScript;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

class SQLScriptFileParserTest {

    private final SQLScriptFileParser parser = new SQLScriptFileParser();

    @SneakyThrows
    private String getFileContent(String filename) {
        // FIXME: 28.09.2021 REWRITE!!!!!!!!!!!!
        return String.join("\n", Files.readAllLines(
                new File(getClass().getClassLoader().getResource("sql/" + filename)
                        .toURI()).toPath()));
    }

    @Test
    @SneakyThrows
    void shouldParseScriptFiles() {
        final String expected = getFileContent("Create_Notes_Table.sql");
        final String actual = parser.parse(SQLScript.CREATE_NOTES_TABLE);

        Assertions.assertEquals(expected, actual);
    }
}