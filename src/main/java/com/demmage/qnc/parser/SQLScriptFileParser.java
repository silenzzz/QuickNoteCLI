package com.demmage.qnc.parser;

import com.demmage.qnc.parser.constants.SQLScript;
import com.demmage.qnc.parser.exception.SQLScriptParseException;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class SQLScriptFileParser {

    private final EnumMap<SQLScript, File> scripts = new EnumMap<>(SQLScript.class);

    public SQLScriptFileParser() {

        try {
            Enumeration<URL> urls = getClass().getClassLoader().getResources("sql");

            URL dirUrl = Collections.list(urls).get(0);
            File dir = new File(dirUrl.getFile());

            File[] scriptFiles = dir.listFiles();

            assert scriptFiles != null;
            Arrays.stream(scriptFiles).forEach(f -> scripts.put(SQLScript.valueOfFilename(f.getName()), f));

        } catch (IOException | AssertionError e) {
            throw new SQLScriptParseException("SQL Script Parse Fail", e);
        }
    }

    @SneakyThrows
    public String parse(SQLScript name) {
        return String.join("\n", Files.readAllLines(scripts.get(name).toPath()));
    }
}
