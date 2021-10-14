package com.demmage.qnc.dao;

import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.connection.ConnectionProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteDAOTest {

    private static NoteDAO noteDao;

    private static Note random() {
        return new Note(RandomStringUtils.random(5), RandomStringUtils.random(50), Timestamp.from(Instant.now()), RandomStringUtils.random(8));
    }
    
    @Test
    @BeforeAll
    static void shouldCreateNotesTable() { //NOSONAR
        noteDao = new NoteDAO(new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver"));
    }

    @Test
    @Order(1)
    void shouldCreateMultipleNotes() {
        final List<Note> expected = new ArrayList<>();
        Collections.addAll(expected,
                new Note("NAME", "CONTENT", Timestamp.from(Instant.now()), "HASH"),
                random(),
                random(),
                random());

        expected.forEach(noteDao::createNewNote);

        final List<Note> actual = noteDao.getAllNotes();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void shouldDeleteNoteByName() {
        boolean result = noteDao.deleteNote("NAME");

        Assertions.assertEquals(3, noteDao.getAllNotesCount());
        Assertions.assertTrue(result);
    }

    @Test
    @Order(3)
    void shouldClearNotesTable() {
        boolean result = noteDao.deleteAllNotes();

        final List<Note> notes = noteDao.getAllNotes();

        Assertions.assertEquals(0, notes.size());
        Assertions.assertTrue(result);
    }

    @Test
    @Order(4)
    void shouldCreateNewNote() {
        final Note expected = new Note("NAME", "CONTENT", Timestamp.from(Instant.now()), "HASH");
        noteDao.createNewNote(expected);

        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void shouldAppendContentToLastNote() {
        final String append = "APPENDED_CONTENT";
        final Note expected = noteDao.getLastNote();
        expected.setContent("CONTENT\n" + append);

        boolean result = noteDao.appendLastNote(append, "HASH");
        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(6)
    void shouldRenameLastNote() {
        final String newName = "RENAMED NOTE";
        final Note expected = noteDao.getLastNote();
        expected.setName(newName);

        boolean result = noteDao.renameLastNote(newName);
        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(7)
    void shouldReturnAllNotesCount() {
        final int expected = 1;
        final int actual = noteDao.getAllNotesCount();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(8)
    void shouldReturnNoteByName() {
        Assertions.assertNotNull(noteDao.getNoteByName("RENAMED NOTE"));
    }
}