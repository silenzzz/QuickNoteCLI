package com.demmage.qnc.service;

import com.demmage.qnc.dao.NoteDAO;
import com.demmage.qnc.domain.Note;
import com.demmage.qnc.parser.ConnectionPropertiesParser;
import com.demmage.qnc.util.DefaultNameGenerator;
import com.demmage.qnc.util.HashCalculator;

import java.sql.Timestamp;

public class NoteService {

    private final NoteDAO dao;
    private final HashCalculator hashCalc = new HashCalculator();
    private final DefaultNameGenerator nameGenerator = new DefaultNameGenerator();

    public NoteService() {
        dao = new NoteDAO(new ConnectionPropertiesParser().getProperties());
    }

    public void createNewNote(String content) {
        dao.createNewNote(new Note(nameGenerator.generate(), content, new Timestamp(System.currentTimeMillis()), hashCalc.calculateMd5(content)));
    }

    public void createNewNote(String name, String content) {
        dao.createNewNote(new Note(name, content, new Timestamp(System.currentTimeMillis()), hashCalc.calculateMd5(content)));
    }

    public Note getLast() {
        return dao.getLastNote();
    }

    public void appendToLast(String content) {
        dao.appendLastNote(content);
    }

    public void renameLast(String newName) {
        dao.renameLastNote(newName);
    }
}
