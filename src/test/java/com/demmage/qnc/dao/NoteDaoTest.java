package com.demmage.qnc.dao;

import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.entities.ConnectionProperties;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteDaoTest {

    private static NoteDao noteDao;

    @Test
    @BeforeAll
    @Order(1)
    public static void shouldCreateNotesTable() {
        noteDao = new NoteDao(new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver"));
    }

    @Test
    @Order(2)
    public void shouldCreateNewNote() {
        final Note expected = new Note("NAME", "CONTENT", Timestamp.from(Instant.now()), "HASH");
        noteDao.createNewNote(expected);

        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    public void shouldClearNotesTable() {
        noteDao.clearNotes();

        final List<Note> notes = noteDao.getAllNotes();

        Assertions.assertEquals(0, notes.size());
    }

    @Test
    @Order(4)
    public void shouldCreateMultipleNotes() {

        final List<Note> expected = new ArrayList<>();
        Collections.addAll(expected, new Note("NAME1", "CONTENT1", Timestamp.from(Instant.now()), "HASH1"),
                new Note("NAME2", "CONTENT2", Timestamp.from(Instant.now()), "HASH2"),
                new Note("NAME3", "CONTENT3", Timestamp.from(Instant.now()), "HASH3"),
                new Note("NAME4", "CONTENT4", Timestamp.from(Instant.now()), "HASH4"));

        expected.forEach(noteDao::createNewNote);

        final List<Note> actual = noteDao.getAllNotes();

        Assertions.assertEquals(expected, actual);
    }
}