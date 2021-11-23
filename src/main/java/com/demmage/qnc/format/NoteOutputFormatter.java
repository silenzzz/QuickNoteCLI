package com.demmage.qnc.format;

import com.demmage.qnc.actions.Note;
import dnl.utils.text.table.TextTable;

import java.util.List;

public class NoteOutputFormatter {

    private static final String[] LIST_HEADERS = new String[]{"HASH", "NAME", "DATE", "SHORT CONTENT "};
    private static final String[] SINGLE_HEADERS = new String[]{"HASH", "NAME ", "DATE"};

    public void printList(List<Note> notes) {

        final String[][] data = new String[notes.size()][4];

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            int subContentEnd = Math.min(note.getContent().length(), 12);

            data[i][0] = note.getHash().substring(0, 5) + " ";
            data[i][1] = note.getName() + " ";
            data[i][2] = note.getCreated().toString() + " ";
            data[i][3] = (note.getContent().substring(0, subContentEnd)).replace("\n", "") + " ";
        }

        TextTable table = new TextTable(LIST_HEADERS, data);
        table.setAddRowNumbering(true);

        table.printTable();
    }

    public void printNote(Note note) {
        final String[][] data = new String[1][3];

        data[0][0] = note.getHash().substring(0, 5) + " ";
        data[0][1] = note.getName() + " ";
        data[0][2] = note.getCreated().toString() + " ";

        new TextTable(SINGLE_HEADERS, data).printTable();

        System.out.println("\n\n" + note.getContent());
    }
}
