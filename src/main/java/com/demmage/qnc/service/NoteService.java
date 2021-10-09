package com.demmage.qnc.service;

import com.demmage.qnc.dao.NoteDAO;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionPropertiesParser;
import com.demmage.qnc.util.DefaultNameGenerator;
import com.demmage.qnc.util.HashCalculator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class NoteService {

    private final NoteDAO dao;
    private final HashCalculator hashCalc = new HashCalculator();
    private final DefaultNameGenerator nameGenerator = new DefaultNameGenerator();

    private static final Note EMPTY_NOTE = new Note("", "", Timestamp.from(Instant.now()), "     ");

    private Note lastCached;

    public NoteService() {
        dao = new NoteDAO(new ConnectionPropertiesParser().getProperties());
    }

    public void createNew(String content) {
        create(null, content);
    }

    public void createNew(String name, String content) {
        create(name, content);
    }

    private void create(String name, String content) {
        try {
            Note note = new Note(name == null ? nameGenerator.generate() : name, content, Timestamp.from(Instant.now()), hashCalc.calculateMd5(content));
            dao.createNewNote(note);
            lastCached = note;
        } catch (Exception e) {
            printError(e);
        }
    }

    private boolean isNotesTableEmpty() {
        return dao.getAllNotesCount() == 0;
    }

    public void deleteAll() {
        try {
            dao.deleteAllNotes();
        } catch (Exception e) {
            printError(e);
        }
    }

    public Note getLast() {
        try {
            if (emptyNotes()) {
                return EMPTY_NOTE;
            }

            if (lastCached == null) {
                lastCached = dao.getLastNote();
            }
            return lastCached;
        } catch (Exception e) {
            printError(e);
        }
        return null;
    }

    public void deleteLast() {
        try {
            dao.deleteNote(dao.getLastNote().getName());
            if (isNotesTableEmpty()) {
                lastCached = null;
            } else {
                lastCached = dao.getLastNote();
            }
        } catch (Exception e) {
            printError(e);
        }
    }

    public Note getByName(String name) {
        try {
            if (emptyNotes()) {
                return EMPTY_NOTE;
            }

            return dao.getNoteByName(name);
        } catch (Exception e) {
            printError(e);
        }
        return null;
    }

    public void delete(String name) {
        try {
            dao.deleteNote(name);
        } catch (Exception e) {
            printError(e);
        }
    }

    public void appendToLast(String appendedContent) {
        try {
            if (lastCached == null) {
                lastCached = dao.getLastNote();
            }

            final String content = lastCached.getContent() + "\n" + appendedContent;
            final String hash = hashCalc.calculateMd5(content);

            lastCached.setContent(content);
            lastCached.setHash(hash);

            dao.appendLastNote(appendedContent, hash);
        } catch (Exception e) {
            printError(e);
        }
    }

    private boolean emptyNotes() {
        return isNotesTableEmpty();
    }

    public void renameLast(String newName) {
        try {
            if (lastCached == null) {
                lastCached = dao.getLastNote();
            }

            lastCached.setName(newName);
            dao.renameLastNote(newName);
        } catch (Exception e) {
            printError(e);
        }
    }

    public List<Note> getAll() {
        try {
            return dao.getAllNotes();
        } catch (Exception e) {
            printError(e);
        }
        return null; //NOSONAR
    }

    public int getAllNotesCount() {
        return dao.getAllNotesCount();
    }

    public void startH2Server() {
        try {
            dao.startH2Server();
        } catch (Exception e) {
            printError(e);
        }
    }

    private void printError(Exception e) {
        System.out.println(e.getMessage());
        System.exit(1);
    }
}
