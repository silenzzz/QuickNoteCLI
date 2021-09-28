package com.demmage.qnc.parser;

import com.demmage.qnc.parser.constants.SqlScriptName;
import com.demmage.qnc.parser.exception.SqlScriptParseException;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class SqlScriptFileParser {

    private final EnumMap<SqlScriptName, File> scripts = new EnumMap<>(SqlScriptName.class);

    public SqlScriptFileParser() {

        try {
            Enumeration<URL> urls = getClass().getClassLoader().getResources("sql");

            URL dirUrl = Collections.list(urls).get(0);
            File dir = new File(dirUrl.getFile());

            File[] scriptFiles = dir.listFiles();

            assert scriptFiles != null;
            Arrays.stream(scriptFiles).forEach(f -> scripts.put(SqlScriptName.valueOfFilename(f.getName()), f));

        } catch (IOException | AssertionError e) {
            throw new SqlScriptParseException("SQL Script Parse Fail", e);
        }
    }

    @SneakyThrows
    public String parse(SqlScriptName name) {
        return String.join("\n", Files.readAllLines(scripts.get(name).toPath()));
    }
}
