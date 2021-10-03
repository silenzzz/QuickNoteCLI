package com.demmage.qnc.service;

import com.demmage.qnc.parser.sql.SQLScriptFileParser;
import com.demmage.qnc.parser.sql.SQLScriptIDEFileParser;
import com.demmage.qnc.parser.sql.SQLScriptJARFileParser;
import com.demmage.qnc.parser.sql.SQLScriptName;

import java.io.File;

public class SQLScriptService {

    private final SQLScriptFileParser parser;

    public SQLScriptService() {
        final File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        parser = jar.isFile() ? new SQLScriptJARFileParser() : new SQLScriptIDEFileParser();
    }

    public String createNotesTable() {
        return parser.get(SQLScriptName.CREATE_NOTES_TABLE);
    }

    public String getLastNoteQuery() {
        return parser.get(SQLScriptName.GET_LAST_NOTE);
    }

    public String createNote() {
        return parser.get(SQLScriptName.CREATE_NOTE);
    }

    public String getAllNotes() {
        return parser.get(SQLScriptName.GET_ALL_NOTES);
    }

    public String clearNotesTable() {
        return parser.get(SQLScriptName.CLEAR_NOTES_TABLE);
    }

    public String appendLastNote() {
        return parser.get(SQLScriptName.APPEND_LAST_NOTE);
    }

    public String renameLastNote() {
        return parser.get(SQLScriptName.RENAME_LAST_NOTE);
    }

    public String deleteNote() {
        return parser.get(SQLScriptName.DELETE_NOTE);
    }
}