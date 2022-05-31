package fr.zhykos.ymodel.infra.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZipResultTests {

    @Test
    @DisplayName("Zip some strings into a single file")
    void zip() throws IOException {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        final Map<String, String> filesContent = Map.of("file01.txt", "foo", "file02.txt", "hello there");

        final Path junitPath = fileSystem.getPath("junit");
        Files.createDirectory(junitPath);
        final Path zipPath = Files.createTempFile(junitPath, "result", ".zip");
        try (OutputStream outputStream = Files.newOutputStream(zipPath)) {
            new ZipResult().zip(filesContent, outputStream);
        }

        Assertions.assertTrue(Files.mismatch(zipPath, Path.of("src/test/resources/expected01.zip")) < 0,
                () -> {
                    final var debugPath = Path.of("src/test/resources/debug/expected01.zip");
                    var assertMessage = "Files don't match! Generated zip is: '%s'"
                            .formatted(debugPath.toAbsolutePath());
                    try {
                        Files.copy(zipPath, debugPath);
                    } catch (IOException e) {
                        assertMessage += "\nCannot export generated zip because of: " + e.getMessage();
                    }
                    return assertMessage;
                });
    }

}
