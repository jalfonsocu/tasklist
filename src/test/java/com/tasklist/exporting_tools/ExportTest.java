package com.tasklist.exporting_tools;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ExportTest {
    @Test
    public void testExportJSON() throws IOException {
        File tempFile = File.createTempFile("tempFile", ".json");
        Crude crude = new Crude();
        crude.setName("Coso");
        Export.exportJSON(tempFile, crude);
        String expectedContent = "\"name\":\"Coso\"";
        String actualContent = new String(java.nio.file.Files.readAllBytes(tempFile.toPath()));
        assertTrue(actualContent.contains(expectedContent));
        tempFile.delete();
    }

    @Test
    public void testExportCSV() {

    }

    @Test
    public void testExportJSON2() {

    }

    @Test
    public void testExportPDF() {

    }
}
