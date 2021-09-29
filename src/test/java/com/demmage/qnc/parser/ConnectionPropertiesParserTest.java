package com.demmage.qnc.parser;

import com.demmage.qnc.parser.entities.ConnectionProperties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionPropertiesParserTest {

    private final ConnectionPropertiesParser parser = new ConnectionPropertiesParser();

    @SneakyThrows
    @Test
    void shouldParseProperties() {
        final ConnectionProperties expected = new ConnectionProperties("jdbc:h2:file:C:/qnc/db", "", "", "org.h2.Driver");
        final ConnectionProperties actual = parser.getProperties();

        Assertions.assertEquals(expected, actual);
    }
}