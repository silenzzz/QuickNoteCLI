package com.demmage.qnc;

import com.demmage.qnc.args.CmdArgs;
import com.demmage.qnc.service.NoteService;
import org.apache.commons.cli.*;

public class Main {

    private static final Options options = new Options();
    private static final NoteService service = new NoteService();

    static {
        options.addOption(CmdArgs.CREATE_NOTE.toOption());
        options.addOption(CmdArgs.NOTE_NAME.toOption());
        options.addOption(CmdArgs.APPEND_LAST.toOption());
        options.addOption(CmdArgs.NOTE_LIST.toOption());
        options.addOption(CmdArgs.INTERACTIVE_LIST.toOption());
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (UnrecognizedOptionException e) {
            helpFormatter.printHelp(" ", options);
        }



    }
}
