package com.demmage.qnc.dao;

import com.demmage.qnc.parser.SqlScriptFileParser;
import com.demmage.qnc.parser.constants.SqlScriptName;

public class SqlScriptService {

    private final SqlScriptFileParser parser = new SqlScriptFileParser();

    public String getCreateNotesTableQuery() {
        return parser.parse(SqlScriptName.CREATE_NOTES_TABLE);
    }
}
