package com.demmage.qnc.parser.connection;

import lombok.Data;

@Data
public class ConnectionProperties {

    private String url;
    private String user;
    private String password;

    private String driver;

    public ConnectionProperties(String url, String user, String password, String driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }
}
