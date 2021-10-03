package com.demmage.qnc.parser;

import com.demmage.qnc.parser.connection.ConnectionProperties;
import com.demmage.qnc.parser.connection.ConnectionPropertiesParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionPropertiesParserTest {

    private final ConnectionPropertiesParser parser = new ConnectionPropertiesParser();

    @SneakyThrows
    @Test
    void shouldParseProperties() {
        final ConnectionProperties expected = new ConnectionProperties("jdbc:h2:mem:testdb", "", "", "org.h2.Driver");
        final ConnectionProperties actual = parser.getProperties();

        Assertions.assertEquals(expected, actual);
    }
}