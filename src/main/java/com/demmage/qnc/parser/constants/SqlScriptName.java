package com.demmage.qnc.parser.constants;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SqlScriptName {

    CREATE_NOTES_TABLE("Create_Notes_Table.sql");

    private final String fileName;

    SqlScriptName(String fileName) {
        this.fileName = fileName;
    }

    public static SqlScriptName valueOfFileName(String fileName) {
        return Arrays.stream(values()).filter(e -> e.fileName.equals(fileName)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public String getFileName() {
        return fileName;
    }
}
