package com.demmage.qnc.parser;

import com.demmage.qnc.parser.sql.SQLScriptFileParser;
import com.demmage.qnc.parser.sql.SQLScriptIDEFileParser;
import com.demmage.qnc.parser.sql.SQLScriptName;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

class SQLScriptIdeFileParserTest {

    private final SQLScriptFileParser parser = new SQLScriptIDEFileParser();

    @SneakyThrows
    private String getFileContent(String filename) {
        return String.join("\n", Files.readAllLines(
                new File(getClass().getClassLoader().getResource("sql/" + filename)
                        .toURI()).toPath()));
    }

    @Test
    @SneakyThrows
    void shouldParseScriptFiles() {
        final String expected = getFileContent("Create_Notes_Table.sql");
        final String actual = parser.get(SQLScriptName.CREATE_NOTES_TABLE);

        Assertions.assertEquals(expected, actual);
    }
}