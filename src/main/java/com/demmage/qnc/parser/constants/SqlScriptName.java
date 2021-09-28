package com.demmage.qnc.parser.constants;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SqlScriptName {

    CREATE_NOTES_TABLE("Create_Notes_Table.sql"),
    GET_LAST_NOTE("Get_Last_Note.sql"),
    CREATE_NOTE("Create_Note.sql");

    private final String filename;

    SqlScriptName(String filename) {
        this.filename = filename;
    }

    public static SqlScriptName valueOfFilename(String filename) {
        return Arrays.stream(values()).filter(e -> e.filename.equals(filename)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public String getFilename() {
        return filename;
    }
}