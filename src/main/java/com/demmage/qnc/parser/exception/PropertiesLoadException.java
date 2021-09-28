package com.demmage.qnc.parser.exception;

public class PropertiesLoadException extends RuntimeException {
    public PropertiesLoadException() {
    }

    public PropertiesLoadException(String message) {
        super(message);
    }

    public PropertiesLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
