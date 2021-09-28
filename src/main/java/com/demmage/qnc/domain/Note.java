package com.demmage.qnc.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Note {

    private String name;
    private String content;
    private Date created;
    private String hash;
}
