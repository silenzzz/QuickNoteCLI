package com.demmage.qnc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {

    //@Test
    @SneakyThrows
    void shouldStartH2Server() {
        // TODO: 03.10.2021 Rewrite
        new Thread(() ->
        {
            try {
                Main.main(new String[]{"-d"});
                Thread.sleep(3000L);
            } catch (Exception e) {
                fail();
            }
        }).start();
        Thread.sleep(3000L);
    }
}