package com.demmage.qnc.util;

public enum Constants {

    NOTE_NAME_FORMAT("HH:mm");

    private final String param;

    Constants(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
