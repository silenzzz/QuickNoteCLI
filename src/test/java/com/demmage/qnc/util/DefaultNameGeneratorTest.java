package com.demmage.qnc.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DefaultNameGeneratorTest {

    private final DefaultNameGenerator generator = new DefaultNameGenerator();

    @Test
    void shouldGenerateDefaultName() {
        final String expected = new SimpleDateFormat(Constants.NOTE_NAME_STRING_FORMAT.getParam()).format(new Date());
        final String actual = generator.generate();

        Assertions.assertEquals(expected, actual);
    }
}