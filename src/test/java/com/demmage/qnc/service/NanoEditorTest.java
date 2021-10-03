package com.demmage.qnc.service;

import com.demmage.qnc.editor.NanoEditor;
import org.junit.jupiter.api.Test;

class NanoEditorTest {

    private static final NanoEditor service = new NanoEditor();

    @Test
    void should() {
        if (service.installed()) {
            service.getContent();
        }
    }

}