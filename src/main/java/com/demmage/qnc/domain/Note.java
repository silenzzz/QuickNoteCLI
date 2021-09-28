package com.demmage.qnc.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Note {

    private String name;
    private String content;
    private Timestamp created;
    private String hash;

    public Note() {
    }

    public Note(String name, String content, Timestamp created, String hash) {
        this.name = name;
        this.content = content;
        this.created = created;
        this.hash = hash;
    }
}
