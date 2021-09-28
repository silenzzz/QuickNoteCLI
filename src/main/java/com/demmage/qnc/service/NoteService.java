package com.demmage.qnc.service;

import com.demmage.qnc.dao.NoteDao;
import com.demmage.qnc.parser.ConnectionPropertiesParser;
import com.demmage.qnc.util.HashCalculator;

public class NoteService {

    private final NoteDao dao;
    private final HashCalculator hashCalc = new HashCalculator();

    public NoteService() {
        dao = new NoteDao(new ConnectionPropertiesParser().getProperties());
    }

    public void createNewNote(String content) {

    }

    public void createNewNote(String name, String content) {

    }
}
