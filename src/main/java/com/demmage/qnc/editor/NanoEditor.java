package com.demmage.qnc.editor;

import com.demmage.qnc.util.OS;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class NanoEditor extends Editor {

    @Override
    public boolean installed() {
        try {
            Process process;
            if (OS.IS_WINDOWS) {
                process = new ProcessBuilder("nano").start();
            } else {
                process = Runtime.getRuntime().exec("bash nano");
            }
            return process.waitFor(2, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException ignored) { //NOSONAR
            return false;
        }
    }

    @SneakyThrows
    @Override
    public String getContent() {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();

        startProcess(file.getAbsolutePath());

        return String.join("\n", Files.readAllLines(Paths.get(file.getPath())));
    }

    @SneakyThrows
    @Override
    protected void startProcess(String absolutePath) {
        Process process = new ProcessBuilder("nano", absolutePath).inheritIO().start();
        process.waitFor();
    }
}
