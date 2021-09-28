package com.demmage.qnc.service;

import com.demmage.qnc.dao.NoteDao;
import com.demmage.qnc.parser.ConnectionPropertiesParser;

public class NoteService {

    private final NoteDao dao;

    public NoteService() {
        dao = new NoteDao(new ConnectionPropertiesParser().getProperties());
    }

    public void createNewNote(String content) {

    }

    public void createNewNote(String name, String content) {

    }

}
