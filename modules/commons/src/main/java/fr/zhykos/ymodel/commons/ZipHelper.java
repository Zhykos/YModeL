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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.zhykos.ymodel.commons.models.ZipFile;

public final class ZipHelper {

    private ZipHelper() {
        // Helper class: do nothing and must not be called
    }

    public static List<ZipFile> unzip(final Path zipPath) throws IOException {
        return unzip(Files.newInputStream(zipPath));
    }

    public static List<ZipFile> unzip(final byte[] zipContents) throws IOException {
        return unzip(new ByteArrayInputStream(zipContents));
    }

    private static List<ZipFile> unzip(final InputStream inputStream) throws IOException {
        final List<ZipFile> zipFiles = new ArrayList<>();
        final byte[] buffer = new byte[1024];
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                zipFiles.add(new ZipFile(zipEntry.getName(), outputStream.toString()));
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
        }
        return zipFiles.stream().sorted((gen1, gen2) -> gen1.getFilename().compareTo(gen2.getFilename())).toList();
    }

}
