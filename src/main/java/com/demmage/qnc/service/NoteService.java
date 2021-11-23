package com.demmage.qnc.service;

import com.demmage.qnc.actions.Note;
import com.demmage.qnc.dao.NoteDAO;
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
        Note note = new Note(name == null ? nameGenerator.generate() : name, content, Timestamp.from(Instant.now()), hashCalc.calculateMd5(content));
        dao.createNewNote(note);
        lastCached = note;
    }

    private boolean isNotesTableEmpty() {
        return dao.getAllNotesCount() == 0;
    }

    public void deleteAll() {
        dao.deleteAllNotes();
    }

    public Note getLast() {
        validateCache();
        return lastCached;
    }

    public void deleteLast() {
        dao.deleteNote(dao.getLastNote().getName());

        if (isNotesTableEmpty()) {
            lastCached = null;
        } else {
            lastCached = dao.getLastNote();
        }
    }

    public Note getByName(String name) {
        return dao.getNoteByName(name);
    }

    public void delete(String name) {
        dao.deleteNote(name);
    }

    public void appendToLast(String appendedContent) {
        validateCache();

        final String content = lastCached.getContent() + "\n" + appendedContent;
        final String hash = hashCalc.calculateMd5(content);

        lastCached.setContent(content);
        lastCached.setHash(hash);

        dao.appendLastNote(appendedContent, hash);
    }

    public void renameLast(String newName) {
        validateCache();

        lastCached.setName(newName);
        dao.renameLastNote(newName);
    }

    private void validateCache() {
        if (lastCached == null) {
            lastCached = dao.getLastNote();
        }
    }

    public List<Note> getAll() {
        return dao.getAllNotes();
    }

    public int getAllNotesCount() {
        return dao.getAllNotesCount();
    }

    public void startH2Server() {
        dao.startH2Server();
    }
}
