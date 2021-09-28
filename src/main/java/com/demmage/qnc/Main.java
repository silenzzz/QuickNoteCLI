package com.demmage.qnc;

import com.demmage.qnc.args.Args;
import com.demmage.qnc.service.NoteService;
import org.apache.commons.cli.*;

public class Main {

    private static final Options options = new Options();
    private static final NoteService service = new NoteService();

    static {
        options.addOption(Args.CREATE_NOTE.toOption());
        options.addOption(Args.NOTE_NAME.toOption());
        options.addOption(Args.APPEND_LAST.toOption());
        options.addOption(Args.NOTE_LIST.toOption());
        options.addOption(Args.INTERACTIVE_LIST.toOption());
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
