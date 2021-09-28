package com.demmage.qnc.dao;

import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.entities.ConnectionProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

public class NoteDaoTest {

    private static NoteDao noteDao;

    @Test
    @BeforeAll
    public static void shouldCreateNotesTable() {
        noteDao = new NoteDao(new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver"));
    }

    @Test
    public void shouldCreateNewNote() {
        final Note expected = new Note("NAME", "CONTENT", Timestamp.from(Instant.now()), "HASH");
        noteDao.createNewNote(expected);

        final Note actual = noteDao.getLastNote();

        Assertions.assertEquals(expected, actual);
    }
}