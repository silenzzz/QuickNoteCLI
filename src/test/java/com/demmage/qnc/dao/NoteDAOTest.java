package com.demmage.qnc.dao;

import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionProperties;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteDAOTest {

    private static NoteDAO noteDao;

    @Test
    @BeforeAll
    static void shouldCreateNotesTable() {
        noteDao = new NoteDAO(new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver"));
    }

    @Test
    @Order(1)
    void shouldCreateMultipleNotes() {

        final List<Note> expected = new ArrayList<>();
        Collections.addAll(expected, new Note("NAME1", "CONTENT1", Timestamp.from(Instant.now()), "HASH1"),
                new Note("NAME2", "CONTENT2", Timestamp.from(Instant.now()), "HASH2"),
                new Note("NAME3", "CONTENT3", Timestamp.from(Instant.now()), "HASH3"),
                new Note("NAME4", "CONTENT4", Timestamp.from(Instant.now()), "HASH4"));

        expected.forEach(noteDao::createNewNote);

        final List<Note> actual = noteDao.getAllNotes();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void shouldClearNotesTable() {
        noteDao.deleteAllNotes();

        final List<Note> notes = noteDao.getAllNotes();

        Assertions.assertEquals(0, notes.size());
    }

    @Test
    @Order(3)
    void shouldCreateNewNote() {
        final Note expected = new Note("NAME", "CONTENT", Timestamp.from(Instant.now()), "HASH");
        noteDao.createNewNote(expected);

        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    void shouldAppendContentToLastNote() {
        final String append = "APPENDED_CONTENT";
        final Note expected = noteDao.getLastNote();
        expected.setContent("CONTENT\n" + append);

        noteDao.appendLastNote(append, "HASH");
        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void shouldRenameLastNote() {
        final String newName = "RENAMED NOTE";
        final Note expected = noteDao.getLastNote();
        expected.setName(newName);

        noteDao.renameLastNote(newName);
        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    void shouldReturnAllNotesCount() {
        final int expected = 1;
        final int actual = noteDao.getAllNotesCount();

        Assertions.assertEquals(expected, noteDao.getAllNotes().size());
    }
}