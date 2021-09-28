package com.demmage.qnc.dao.exception;

public class DaoParamException extends DaoException {
    public DaoParamException() {
        super();
    }

    public DaoParamException(String message) {
        super(message);
    }

    public DaoParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
