package com.demmage.qnc.editor;

public abstract class Editor {

    public abstract boolean installed();

    public abstract String getContent();

    protected abstract void startProcess(String absolutePath);
}
