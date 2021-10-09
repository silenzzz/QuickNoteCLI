package com.demmage.qnc.dao.exception;

public class RequestUnsupportedParamException extends DaoException {

    public RequestUnsupportedParamException() {
        super();
    }

    public RequestUnsupportedParamException(String message) {
        super(message);
    }

    public RequestUnsupportedParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
