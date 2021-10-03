package com.demmage.qnc;

import com.demmage.qnc.format.NoteOutputFormatter;
import com.demmage.qnc.service.NoteService;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Scanner;

import static com.demmage.qnc.args.CmdArgs.*;

public class Main {

    private static final Options options = new Options();
    private static final NoteService service = new NoteService();
    private static final NoteOutputFormatter formatter = new NoteOutputFormatter();
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    private static final Scanner input = new Scanner(System.in);

    static {
        options.addOption(NEW_NOTE_NAME.toOption());
        options.addOption(RENAME_LAST_NOTE.toOption());
        options.addOption(APPEND_LAST_NOTE.toOption());
        options.addOption(PRINT_LAST_NOTE.toOption());
        options.addOption(DELETE_LAST_NOTE.toOption());
        options.addOption(CLEAR_ALL_NOTES.toOption());

        options.addOption(NOTE_LIST.toOption());
        //options.addOption(INTERACTIVE_LIST.toOption());

        options.addOption(START_DB_SERVER.toOption());

        options.addOption(HELP.toOption());
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (Arrays.equals(cmd.getOptions(), new Option[0])) {
                createNewNote();
                return;
            }
        } catch (UnrecognizedOptionException | MissingArgumentException e) {
            printHelp();
            return;
        }

        if (cmd.hasOption(START_DB_SERVER.getOpt())) {
            service.startH2Server();
        } else if (cmd.hasOption(APPEND_LAST_NOTE.getOpt())) {
            service.appendToLast(getNoteContentInput());
        } else if (cmd.hasOption(NOTE_LIST.getOpt())) {
            System.out.println(formatter.formatOutputList(service.getAll()));
        } else if (cmd.hasOption(PRINT_LAST_NOTE.getOpt())) {
            System.out.println(formatter.format(service.getLast()));
        } else if (cmd.hasOption(HELP.getOpt())) {
            printHelp();
        } else if (cmd.hasOption(RENAME_LAST_NOTE.getOpt())) {
            service.renameLast(cmd.getOptionValue(RENAME_LAST_NOTE.getOpt()));
        } else if (cmd.hasOption(DELETE_LAST_NOTE.getOpt())) {
            service.deleteLast();
        } else if (cmd.hasOption(CLEAR_ALL_NOTES.getOpt()) && confirmAction()) {
            service.deleteAll();
        } else if (cmd.hasOption(NEW_NOTE_NAME.getOpt())) {
            String name = cmd.getOptionValue(NEW_NOTE_NAME.getOpt());
            String content = getNoteContentInput();
            service.createNew(name, content);
        }
    }

    private static void printHelp() {
        helpFormatter.printHelp(" ", options);
        System.out.println("Run QNC without arguments to create new note");
    }

    private static void createNewNote() {
        String content = getNoteContentInput();
        service.createNew(content);
    }

    private static boolean confirmAction() {
        System.out.println("You sure? (Y/N)");
        return input.nextLine().equalsIgnoreCase("Y");
    }

    private static String getNoteContentInput() {
        System.out.println("Start typing text. To save write NE on new line");
        StringBuilder content = new StringBuilder();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.equals("NE")) {
                return content.toString();
            }
            content.append(line).append("\n");
        }
        return content.toString();
    }
}
