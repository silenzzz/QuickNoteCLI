package com.demmage.qnc.format;

import com.demmage.qnc.domain.Note;

import java.util.List;

public class NoteOutputFormatter {

    private static final String HEADER = "| HASH | NAME | DATE |";

    public String formatOutputList(List<Note> notes) {
        StringBuilder result = new StringBuilder();
        result.append("| N ").append(HEADER).append(" SHORT CONTENT |\n\n");

        final int[] i = {1};
        notes.forEach(n -> {
            String content = n.getContent();
            int subContentEnd = Math.min(content.length(), 5);

            result.append(i[0]).append(". | ").append(n.getHash(), 0, 5)
                    .append(" | ").append(n.getName()).append(" | ").append(n.getCreated().toString(), 0, 16)
                    .append(" | ").append(n.getContent().replace("\n", " "), 0, subContentEnd).append("\n");
            i[0]++;
        });

        return result.toString();
    }

    public String format(Note note) {
        return new StringBuilder().append(HEADER).append("\n| ")
                .append(note.getHash(), 0, 5).append(" | ")
                .append(note.getName()).append(" | ")
                .append(note.getCreated().toString(), 0, 16).append(" |\n\n")
                .append(note.getContent()).toString();
    }
}
