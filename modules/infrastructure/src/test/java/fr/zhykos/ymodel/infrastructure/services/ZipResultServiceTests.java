/*
 * Copyright 2022 Thomas "Zhykos" Cicognani.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.zhykos.ymodel.infrastructure.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import fr.zhykos.ymodel.commons.ZipHelper;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.services.ZipResultService.ZipException;

class ZipResultServiceTests {

    @Test
    @DisplayName("Zip some strings into a single file")
    void zip() throws IOException, ZipException {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        final List<GeneratedFile> generatedFiles = List.of(new GeneratedFile("file01.txt", "foo"),
                new GeneratedFile("file02.txt", "hello there"));

        final Path junitPath = fileSystem.getPath("junit");
        Files.createDirectory(junitPath);
        final Path zipPath = Files.createTempFile(junitPath, "result", ".zip");
        try (OutputStream outputStream = Files.newOutputStream(zipPath)) {
            new ZipResultService().zip(generatedFiles, outputStream);
        }

        Assertions.assertEquals(generatedFiles, ZipHelper.unzip(zipPath).stream()
                .map(zip -> new GeneratedFile(zip.getFilename(), zip.getContents())).toList());
    }

    @Test
    @DisplayName("Zip exception")
    void zipException() throws IOException {
        final List<GeneratedFile> generatedFiles = List.of(new GeneratedFile("file01.txt", "foo"));
        try (OutputStream outputStream = Mockito.mock(OutputStream.class)) {
            Mockito.doThrow(IOException.class).when(outputStream).write(Mockito.anyInt());
            Assertions.assertThrows(ZipException.class,
                    () -> new ZipResultService().zip(generatedFiles, outputStream));
        }
    }

}
