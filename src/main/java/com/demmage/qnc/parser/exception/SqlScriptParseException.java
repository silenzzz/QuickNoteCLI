package com.demmage.qnc.parser.exception;

public class SqlScriptParseException extends RuntimeException {

    public SqlScriptParseException() {
    }

    public SqlScriptParseException(String message) {
        super(message);
    }

    public SqlScriptParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
