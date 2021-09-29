package com.demmage.qnc.util;

public enum Constants {

    NOTE_NAME_STRING_FORMAT("dd.MM.yyyy HH:mm:ss");

    private final String param;

    Constants(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
