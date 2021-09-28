package com.demmage.qnc.dao;

import com.demmage.qnc.parser.entities.ConnectionProperties;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class NoteDaoTest {

    private NoteDao noteDao;

    @Test
    @BeforeAll
    public void shouldCreateNotesTable() {
        noteDao = new NoteDao(new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver"));
    }

}