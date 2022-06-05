package fr.zhykos.ymodel.infra.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infra.models.GeneratedFile;

class ZipResultTests {

    @Test
    @DisplayName("Zip some strings into a single file")
    void zip() throws IOException {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        final List<GeneratedFile> generatedFiles = List.of(new GeneratedFile("file01.txt", "foo"),
                new GeneratedFile("file02.txt", "hello there"));

        final Path junitPath = fileSystem.getPath("junit");
        Files.createDirectory(junitPath);
        final Path zipPath = Files.createTempFile(junitPath, "result", ".zip");
        try (OutputStream outputStream = Files.newOutputStream(zipPath)) {
            new ZipResultService().zip(generatedFiles, outputStream);
        }

        Assertions.assertEquals(generatedFiles, unzip(zipPath));
    }

    private static List<GeneratedFile> unzip(final Path zipPath) throws IOException {
        final List<GeneratedFile> zipFiles = new ArrayList<>();
        final byte[] buffer = new byte[1024];
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                zipFiles.add(new GeneratedFile(zipEntry.getName(), outputStream.toString()));
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
        }
        return zipFiles;
    }

}
