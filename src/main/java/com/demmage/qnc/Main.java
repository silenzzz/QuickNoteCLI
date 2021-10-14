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
        options.addOption(SHOW_NOTE.toOption());
        options.addOption(DELETE_LAST_NOTE.toOption());
        options.addOption(DELETE_NOTE.toOption());
        options.addOption(CLEAR_ALL_NOTES.toOption());
        options.addOption(NANO.toOption());

        options.addOption(NOTE_LIST.toOption());
        //options.addOption(INTERACTIVE_LIST.toOption());

        options.addOption(START_DB_SERVER.toOption());

        options.addOption(HELP.toOption());
        options.addOption(ABOUT.toOption());
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

        try {
            // TODO: 03.10.2021 Clean this mess
            if (cmd.hasOption(START_DB_SERVER.getOpt())) {
                noteService.startH2Server();
            } else if (cmd.hasOption(DELETE_NOTE.getOpt()) && confirmAction()) {
                noteService.delete(cmd.getOptionValue(DELETE_NOTE.getOpt()));
            } else if (cmd.hasOption(SHOW_NOTE.getOpt())) {
                noteFormatter.printNote(noteService.getByName(cmd.getOptionValue(SHOW_NOTE.getOpt())));
            } else if (cmd.hasOption(NANO.getOpt()) && !cmd.hasOption(APPEND_LAST_NOTE.getOpt()) && !cmd.hasOption(NEW_NOTE_NAME.getOpt())) {
                noteService.createNew(getNanoContentInput());
            } else if (cmd.hasOption(APPEND_LAST_NOTE.getOpt())) {
                noteService.appendToLast(getNoteContentInput());
            } else if (cmd.hasOption(APPEND_LAST_NOTE.getOpt()) && cmd.hasOption(NANO.getOpt())) {
                noteService.appendToLast(getNanoContentInput());
            } else if (cmd.hasOption(NOTE_LIST.getOpt())) {
                noteFormatter.printList(noteService.getAll());
            } else if (cmd.hasOption(PRINT_LAST_NOTE.getOpt())) {
                noteFormatter.printNote(noteService.getLast());
            } else if (cmd.hasOption(HELP.getOpt())) {
                printHelp();
            } else if (cmd.hasOption(RENAME_LAST_NOTE.getOpt())) {
                noteService.renameLast(cmd.getOptionValue(RENAME_LAST_NOTE.getOpt()));
            } else if (cmd.hasOption(DELETE_LAST_NOTE.getOpt()) && confirmAction()) {
                noteService.deleteLast();
            } else if (cmd.hasOption(CLEAR_ALL_NOTES.getOpt()) && confirmAction()) {
                noteService.deleteAll();
            } else if (cmd.hasOption(NEW_NOTE_NAME.getOpt()) && cmd.hasOption(NANO.getOpt())) {
                String name = cmd.getOptionValue(NEW_NOTE_NAME.getOpt());
                String content = getNanoContentInput();
                noteService.createNew(name, content);
            } else if (cmd.hasOption(NEW_NOTE_NAME.getOpt())) {
                String name = cmd.getOptionValue(NEW_NOTE_NAME.getOpt());
                String content = getNoteContentInput();
                noteService.createNew(name, content);
            } else if (cmd.hasOption(ABOUT.getOpt())) {
                printAbout();
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Note not found");
        } catch (Exception e) {
            printError(e);
        }
    }

    private static void printAbout() {
        // TODO: 14.10.2021
        System.out.printf("QNC v%s\nCreated by DeMmAge\nhttps://github.com/DeMmAge/QuickNoteCLI\n", "0.1.0");
    }

    private static void printError(Exception e) {
        System.out.println(e.getMessage());
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
        helpFormatter.printHelp("QNC", "", options,
                "\nRun QNC without arguments to create new note\nPlease report issues at https://github.com/DeMmAge/QuickNoteCLI/issues/new");
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
