package com.demmage.qnc.util;

import lombok.SneakyThrows;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashCalculator {

    private final MessageDigest digest;

    @SneakyThrows
    public HashCalculator() {
        digest = MessageDigest.getInstance("MD5");
    }

    public String calculateMd5(String s) {
        byte[] bytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(bytes).toUpperCase();
    }
}
