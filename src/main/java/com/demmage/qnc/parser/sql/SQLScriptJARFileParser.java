package com.demmage.qnc.parser.sql;

import com.demmage.qnc.parser.sql.exception.SQLScriptParseException;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SQLScriptJARFileParser extends SQLScriptFileParser {

    @SneakyThrows
    public SQLScriptJARFileParser() {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        try (JarFile jar = new JarFile(jarFile)) {
            final Enumeration<JarEntry> entries = jar.entries();

            Collections.list(entries).stream()
                    .filter(e -> e.getName().startsWith("sql/") && !e.isDirectory())
                    .forEach(e -> {
                        try {
                            File f = File.createTempFile("tmp", null);
                            f.deleteOnExit();
                            InputStream stream = jar.getInputStream(e);
                            Files.copy(stream, f.toPath(), StandardCopyOption.REPLACE_EXISTING);

                            scripts.put(SQLScriptName.valueOfFilename(e.getName().replace("sql/", "")),
                                    String.join("\n", Files.readAllLines(f.toPath())));
                        } catch (IOException ex) {
                            throw new SQLScriptParseException("SQL Script Parse Fail", ex);
                        }
                    });
        }
    }

    @Override
    public String get(SQLScriptName name) {
        return scripts.get(name);
    }
}
