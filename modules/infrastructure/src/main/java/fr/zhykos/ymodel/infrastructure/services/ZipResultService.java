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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;

/**
 * Service to create a zip file with the generated files in it
 */
public final class ZipResultService {

    /**
     * Create a zip file with the generated files in it
     *
     * @param generatedFiles Generated files to zip
     * @param outputStream   Stream in which the zip file is created
     * @throws IOException Error while creating the zip
     */
    public void zip(final List<GeneratedFile> generatedFiles, final OutputStream outputStream) throws ZipException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (final GeneratedFile generatedFile : generatedFiles) {
                final ZipEntry zipEntry = new ZipEntry(generatedFile.getFilename());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(generatedFile.getContents().getBytes(StandardCharsets.UTF_8));
            }
        } catch (final IOException e) {
            throw new ZipException(e);
        }
    }

    public static final class ZipException extends Exception {

        private ZipException(final Throwable cause) {
            super(cause);
        }

    }

}
