package com.demmage.qnc.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

class DefaultNameGeneratorTest {

    private final DefaultNameGenerator generator = new DefaultNameGenerator();

    @Test
    void shouldGenerateDefaultName() {
        final String expected = "NOTE " + new SimpleDateFormat(Constants.NOTE_NAME_FORMAT.getParam()).format(new Date());
        final String actual = generator.generate();

        Assertions.assertEquals(expected, actual);
    }
}