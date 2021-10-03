package com.demmage.qnc.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashCalculatorTest {

    private final HashCalculator calculator = new HashCalculator();

    @Test
    void shouldCalculateOneLineContentMd5Hash() {
        final String str = "CONTENT CONTENT";

        final String expected = "CDB245C4A7C4B694649E1F740DEC895A";
        final String actual = calculator.calculateMd5(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCalculateMultilineContentMd5Hash() {
        final String str = "CONTENT CONTENT\nCONTENT CONTENT\nCONTENT\nCONTENT CONTENT";

        final String expected = "5E7FB0AF3C4FC6640D7CDE6C32A31A1D";
        final String actual = calculator.calculateMd5(str);

        Assertions.assertEquals(expected, actual);
    }
}