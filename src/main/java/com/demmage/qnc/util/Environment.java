package com.demmage.qnc.util;

public abstract class Environment {

    public static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    public static final String PATH = System.getenv("PATH");

}
