package com.demmage.qnc.parser.connection;

import com.demmage.qnc.parser.connection.exception.PropertiesLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPropertiesParser {

    public ConnectionProperties getProperties() {
        Properties properties = new Properties();

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(stream);
        } catch (NullPointerException | IOException e) {
            throw new PropertiesLoadException("DB Properties Loader Fail", e);
        }

        return new ConnectionProperties(properties.getProperty("db.url"), properties.getProperty("db.username"),
                properties.getProperty("db.password"), properties.getProperty("db.driver"));
    }
}
