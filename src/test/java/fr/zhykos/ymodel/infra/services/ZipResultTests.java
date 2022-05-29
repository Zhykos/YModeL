package fr.zhykos.ymodel.infra.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ZipResultTests {

    @Test
    @DisplayName("Zip some strings into a single file")
    void zip(@TempDir final File tempDir) throws IOException {
        final Map<String, String> filesContent = Map.of("file01.txt", "foo", "file02.txt", "hello there");

        final File zipFile = File.createTempFile("result", ".zip", tempDir);
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile)) {
            new ZipResult().zip(filesContent, fileOutputStream);
        }

        Assertions.assertTrue(Files.mismatch(zipFile.toPath(), Path.of("src/test/resources/expected01.zip")) < 0,
                () -> "Files don't match! Generated zip is: '%s'".formatted(zipFile.getAbsolutePath()));
    }

}
