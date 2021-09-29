package com.demmage.qnc.parser.exception;

public class SQLScriptParseException extends RuntimeException {

    public SQLScriptParseException() {
    }

    public SQLScriptParseException(String message) {
        super(message);
    }

    public SQLScriptParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
