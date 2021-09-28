package com.demmage.qnc.parser;

import com.demmage.qnc.parser.entities.ConnectionProperties;
import com.demmage.qnc.parser.exception.PropertiesLoadException;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class ConnectionPropertiesParser {

    public ConnectionProperties getProperties() {
        Properties properties = new Properties();

        try {
            File file = new File(getClass().getClassLoader().getResource("db.properties").toURI());

            @Cleanup FileInputStream stream = new FileInputStream(file);
            properties.load(stream);
        } catch (NullPointerException | URISyntaxException | IOException e) {
            throw new PropertiesLoadException("DB Properties Loader Fail", e);
        }

        return new ConnectionProperties(properties.getProperty("db.url"), properties.getProperty("db.username"),
                properties.getProperty("db.password"), properties.getProperty("db.driver"));
    }
}
