package com.demmage.qnc.cli;

import org.apache.commons.cli.CommandLine;

import static com.demmage.qnc.args.CmdArgs.START_DB_SERVER;

public class ActionsFactory {

    public Action get(CommandLine cmd) {

        if (cmd.hasOption(START_DB_SERVER.getOpt())) {
            return new StartH2ServerAction();
        }

        return null;
    }

}
