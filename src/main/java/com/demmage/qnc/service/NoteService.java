package com.demmage.qnc.service;

import com.demmage.qnc.dao.NoteDAO;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionPropertiesParser;
import com.demmage.qnc.util.DefaultNameGenerator;
import com.demmage.qnc.util.HashCalculator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
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
        log.debug("New Note {} Created", note);
    }

    private boolean isNotesTableEmpty() {
        return dao.getAllNotesCount() == 0;
    }

    public void deleteAll() {
        dao.deleteAllNotes();
    }

    public Note getLast() {
        if (!validateEmptyNotesTable()) {
            return new Note("", "Notes empty, create a new one", Timestamp.from(Instant.now()), "     ");
        }

        if (lastCached == null) {
            lastCached = dao.getLastNote();
        }
        return lastCached;
    }

    public void deleteLast() {
        if (!validateEmptyNotesTable()) {
            return;
        }

        dao.deleteNote(dao.getLastNote().getName());
        if (isNotesTableEmpty()) {
            lastCached = null;
        } else {
            lastCached = dao.getLastNote();
        }
    }

    public void appendToLast(String appendedContent) {
        if (!validateEmptyNotesTable()) {
            return;
        }

        if (lastCached == null) {
            lastCached = dao.getLastNote();
        }

        final String content = lastCached.getContent() + "\n" + appendedContent;
        final String hash = hashCalc.calculateMd5(content);

        lastCached.setContent(content);
        lastCached.setHash(hash);

        dao.appendLastNote(appendedContent, hash);
        log.debug("New text appended to last note");
    }

    private boolean validateEmptyNotesTable() {
        if (isNotesTableEmpty()) {
            log.debug("", new NoSuchElementException("Notes table empty"));
            return false;
        }
        return true;
    }

    public void renameLast(String newName) {
        if (lastCached == null) {
            lastCached = dao.getLastNote();
        }

        lastCached.setName(newName);
        dao.renameLastNote(newName);
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
