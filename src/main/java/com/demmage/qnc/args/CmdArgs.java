package com.demmage.qnc.args;

import org.apache.commons.cli.Option;

public enum CmdArgs {

    NEW_NOTE_NAME("n", "name", "Specify note name", true),
    RENAME_LAST_NOTE("r", "rename", "Rename last note", true),
    APPEND_LAST_NOTE("a", "append",  "Append text to the last note", false),
    PRINT_LAST_NOTE("p", "print", "Print last note text", false),
    DELETE_LAST_NOTE("d", "delete", "Delete last note", false),
    CLEAR_ALL_NOTES("c", "clear", "Clear all notes", false),

    NOTE_LIST("l", "list", "Print note list", false),
    INTERACTIVE_LIST("i", "interactive", "Interactive notes list", false),

    HELP("h", "help", "Print help", false),

    START_DB_SERVER("s", "dbs", "Start DB server", false);

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
