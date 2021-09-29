package com.demmage.qnc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultNameGenerator {

    private final SimpleDateFormat format = new SimpleDateFormat(Constants.NOTE_NAME_STRING_FORMAT.getParam());

    public String generate() {
        return format.format(new Date());
    }
}
