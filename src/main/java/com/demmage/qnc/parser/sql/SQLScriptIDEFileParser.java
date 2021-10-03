package com.demmage.qnc.parser.sql;

import com.demmage.qnc.parser.sql.exception.SQLScriptParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class SQLScriptIDEFileParser extends SQLScriptFileParser {

    public SQLScriptIDEFileParser() {
        try {
            File dir = new File(SQLScriptJARFileParser.class.getClassLoader().getResource("sql").getFile());

            File[] scriptFiles = dir.listFiles();

            assert scriptFiles != null;
            Arrays.stream(scriptFiles).forEach(f -> {
                try {
                    scripts.put(SQLScriptName.valueOfFilename(f.getName()),
                            String.join("\n", Files.readAllLines(f.toPath())));
                } catch (IOException e) {
                    throw new SQLScriptParseException("SQL Script Parse Fail", e);
                }
            });

        } catch (AssertionError | NullPointerException e) {
            throw new SQLScriptParseException("SQL Script Parse Fail", e);
        }
    }

    @Override
    public String get(SQLScriptName name) {
        return scripts.get(name);
    }
}
