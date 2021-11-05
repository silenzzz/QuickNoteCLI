package com.demmage.qnc.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class HelpAction implements Action {

    private static final HelpFormatter formatter = new HelpFormatter();

    @Override
    public String launch(Options options) {
        formatter.printHelp("QNC", "", options,
                "\nRun QNC without arguments to create new note\nPlease report issues at https://github.com/DeMmAge/QuickNoteCLI/issues/new");
        return "";
    }
}