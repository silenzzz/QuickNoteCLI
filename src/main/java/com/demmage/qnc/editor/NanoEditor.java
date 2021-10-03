package com.demmage.qnc.editor;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class NanoEditor implements Editor {

    @Override
    public boolean installed() {
        try {
            Process process = Runtime.getRuntime().exec("nano");
            boolean installed = process.waitFor(2, TimeUnit.SECONDS);
            process.destroy();
            return installed;
        } catch (IOException | InterruptedException ignored) {
            return false;
        }
    }

    @SneakyThrows
    @Override
    public String getContent() {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();

        Process process = new ProcessBuilder("nano", file.getAbsolutePath()).inheritIO().start();
        process.waitFor();

        return String.join("\n", Files.readAllLines(Paths.get(file.getPath())));
    }
}
