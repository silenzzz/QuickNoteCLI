package com.demmage.qnc.parser;

import com.demmage.qnc.parser.entities.ConnectionProperties;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionPropertiesParserTest {

    private final ConnectionPropertiesParser parser = new ConnectionPropertiesParser();

    @SneakyThrows
    @Test
    public void shouldParseProperties() {
        final ConnectionProperties expected = new ConnectionProperties("jdbc:h2:file:C:/qnc/db", "", "", "org.h2.Driver");
        final ConnectionProperties actual = parser.getProperties();

        Assert.assertEquals(expected, actual);
    }
}