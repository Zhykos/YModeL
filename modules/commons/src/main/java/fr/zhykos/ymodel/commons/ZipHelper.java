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
package fr.zhykos.ymodel.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.zhykos.ymodel.commons.models.ZipFile;

/**
 * Helper for zip files
 */
public final class ZipHelper {

    /**
     * Buffer size for reading the stream
     */
    private static final int BUFFER_SIZE = 1024;

    private ZipHelper() {
        // Helper class: do nothing and must not be called
    }

    /**
     * Unzip a file
     *
     * @param zipPath The path representing the file to unzip
     * @return Objects representing the unzipped files
     * @throws IOException
     */
    public static List<ZipFile> unzip(final Path zipPath) throws IOException {
        return unzip(Files.newInputStream(zipPath));
    }

    /**
     * Unzip a stream representing a file to unzip
     *
     * @param zipContents The stream (the file to unzip) as a byte array
     * @return O
     * @throws IOException
     */
    public static List<ZipFile> unzip(final byte[] zipContents) throws IOException {
        return unzip(new ByteArrayInputStream(zipContents));
    }

    private static List<ZipFile> unzip(final InputStream inputStream) throws IOException {
        final List<ZipFile> zipFiles = new ArrayList<>();
        final byte[] buffer = new byte[BUFFER_SIZE];
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                zipFiles.add(new ZipFile(zipEntry.getName(), outputStream.toString(StandardCharsets.UTF_8)));
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
        }
        return zipFiles.stream().sorted((gen1, gen2) -> gen1.getFilename().compareTo(gen2.getFilename())).toList();
    }

}
