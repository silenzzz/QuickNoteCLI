package com.demmage.qnc.args;

import org.apache.commons.cli.Option;

public enum CmdArgs {

    CREATE_NOTE("n", "note", "Create new note", false),
    NOTE_NAME("N", "name", "Specify note name", true),
    APPEND_LAST("a", "append",  "Append text to the last note", false),
    NOTE_LIST("l", "list", "Print note list", false),
    INTERACTIVE_LIST("i", "interactive", "Interactive notes list", false);

    private final String opt;
    private final String longOpt;
    private final String description;
    private final boolean hasArg;

    CmdArgs(String opt, String longOpt, String description, boolean hasArg) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.description = description;
        this.hasArg = hasArg;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public Option toOption() {
        return new Option(opt, longOpt, hasArg, description);
    }
}
