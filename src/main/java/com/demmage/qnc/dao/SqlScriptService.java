package com.demmage.qnc.dao;

import com.demmage.qnc.parser.SqlScriptFileParser;
import com.demmage.qnc.parser.constants.SqlScriptName;

// TODO: 28.09.2021 Remove?
public class SqlScriptService {

    private final SqlScriptFileParser parser = new SqlScriptFileParser();

    public String createNotesTable() {
        return parser.parse(SqlScriptName.CREATE_NOTES_TABLE);
    }

    public String getLastNoteQuery() {
        return parser.parse(SqlScriptName.GET_LAST_NOTE);
    }

    public String createNote() {
        return parser.parse(SqlScriptName.CREATE_NOTE);
    }
}
