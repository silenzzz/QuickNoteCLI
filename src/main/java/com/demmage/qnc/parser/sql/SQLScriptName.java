package com.demmage.qnc.parser.sql;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SQLScriptName {

    CREATE_NOTES_TABLE("Create_Notes_Table.sql"),
    GET_LAST_NOTE("Get_Last_Note.sql"),
    CREATE_NOTE("Create_Note.sql"),
    GET_ALL_NOTES("Get_All_Notes.sql"),
    CLEAR_NOTES_TABLE("Clear_Notes_Table.sql"),
    APPEND_LAST_NOTE("Append_Last_Note.sql"),
    DELETE_NOTE("Delete_Note.sql"),
    RENAME_LAST_NOTE("Rename_Last_Note.sql");

    private final String filename;

    SQLScriptName(String filename) {
        this.filename = filename;
    }

    public static SQLScriptName valueOfFilename(String filename) {
        return Arrays.stream(values()).filter(e -> e.filename.equals(filename)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public String getFilename() {
        return filename;
    }
}