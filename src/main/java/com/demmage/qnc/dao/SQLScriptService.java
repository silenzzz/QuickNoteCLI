package com.demmage.qnc.dao;

import com.demmage.qnc.parser.SQLScriptFileParser;
import com.demmage.qnc.parser.constants.SQLScript;

// TODO: 28.09.2021 Remove?
public class SQLScriptService {

    private final SQLScriptFileParser parser = new SQLScriptFileParser();

    public String createNotesTable() {
        return parser.parse(SQLScript.CREATE_NOTES_TABLE);
    }

    public String getLastNoteQuery() {
        return parser.parse(SQLScript.GET_LAST_NOTE);
    }

    public String createNote() {
        return parser.parse(SQLScript.CREATE_NOTE);
    }

    public String getAllNotes() {
        return parser.parse(SQLScript.GET_ALL_NOTES);
    }

    public String clearNotesTable() {
        return parser.parse(SQLScript.CLEAR_NOTES_TABLE);
    }

    public String appendLastNote() { return parser.parse(SQLScript.APPEND_LAST_NOTE); }

    public String renameLastNote() { return parser.parse(SQLScript.RENAME_LAST_NOTE); }
}