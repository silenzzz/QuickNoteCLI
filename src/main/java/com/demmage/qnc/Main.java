package com.demmage.qnc;

import com.demmage.qnc.editor.Editor;
import com.demmage.qnc.editor.NanoEditor;
import com.demmage.qnc.format.NoteOutputFormatter;
import com.demmage.qnc.service.NoteService;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Scanner;

import static com.demmage.qnc.args.CmdArgs.*;

public class Main {

    private static final Options options = new Options();

    private static final NoteService noteService = new NoteService();

    private static final Editor nano = new NanoEditor();

    private static final NoteOutputFormatter noteFormatter = new NoteOutputFormatter();
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    private static final Scanner input = new Scanner(System.in);

    static {
        options.addOption(NEW_NOTE_NAME.toOption());
        options.addOption(RENAME_LAST_NOTE.toOption());
        options.addOption(APPEND_LAST_NOTE.toOption());
        options.addOption(PRINT_LAST_NOTE.toOption());
        options.addOption(DELETE_LAST_NOTE.toOption());
        options.addOption(CLEAR_ALL_NOTES.toOption());
        options.addOption(NANO.toOption());

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

        // TODO: 03.10.2021 Mess
        if (cmd.hasOption(START_DB_SERVER.getOpt())) {
            noteService.startH2Server();
        } else if (cmd.hasOption(NANO.getOpt()) && !cmd.hasOption(APPEND_LAST_NOTE.getOpt())) {
            noteService.createNew(getNanoContentInput());
        } else if (cmd.hasOption(APPEND_LAST_NOTE.getOpt())) {
            noteService.appendToLast(getNoteContentInput());
        } else if (cmd.hasOption(APPEND_LAST_NOTE.getOpt()) && cmd.hasOption(NANO.getOpt())) {
            noteService.appendToLast(getNanoContentInput());
        } else if (cmd.hasOption(NOTE_LIST.getOpt())) {
            System.out.println(noteFormatter.formatOutputList(noteService.getAll()));
        } else if (cmd.hasOption(PRINT_LAST_NOTE.getOpt())) {
            System.out.println(noteFormatter.format(noteService.getLast()));
        } else if (cmd.hasOption(HELP.getOpt())) {
            printHelp();
        } else if (cmd.hasOption(RENAME_LAST_NOTE.getOpt())) {
            noteService.renameLast(cmd.getOptionValue(RENAME_LAST_NOTE.getOpt()));
        } else if (cmd.hasOption(DELETE_LAST_NOTE.getOpt()) && confirmAction()) {
            noteService.deleteLast();
        } else if (cmd.hasOption(CLEAR_ALL_NOTES.getOpt()) && confirmAction()) {
            noteService.deleteAll();
        } else if (cmd.hasOption(NEW_NOTE_NAME.getOpt())) {
            String name = cmd.getOptionValue(NEW_NOTE_NAME.getOpt());
            String content = getNoteContentInput();
            noteService.createNew(name, content);
        } else if (cmd.hasOption(NEW_NOTE_NAME.getOpt()) && cmd.hasOption(NANO.getOpt())) {
            String name = cmd.getOptionValue(NEW_NOTE_NAME.getOpt());
            String content = getNanoContentInput();
            noteService.createNew(name, content);
        } else if (cmd.hasOption(ABOUT.getOpt())) {
            printAbout();
        }
    }

    private static void printAbout() {
        // TODO: 03.10.2021
    }

    private static String getNanoContentInput() {
        if (nano.installed()) {
            return nano.getContent();
        } else {
            printEditorNotInstalled("Nano");
            return "STUB";
        }
    }

    private static void printEditorNotInstalled(String name) {
        System.out.println(name + " not installed");
        System.exit(0);
    }

    private static void printHelp() {
        helpFormatter.printHelp(" ", options);
        System.out.println("Run QNC without arguments to create new note");
    }

    private static void createNewNote() {
        String content = getNoteContentInput();
        noteService.createNew(content);
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
