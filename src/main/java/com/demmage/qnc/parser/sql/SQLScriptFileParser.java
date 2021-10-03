package com.demmage.qnc.parser.sql;

import java.util.EnumMap;

public abstract class SQLScriptFileParser {

    protected final EnumMap<SQLScriptName, String> scripts = new EnumMap<>(SQLScriptName.class);

    protected SQLScriptFileParser() {
    }

    public abstract String get(SQLScriptName name);
}
