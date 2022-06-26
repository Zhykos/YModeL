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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import fr.zhykos.ymodel.infrastructure.models.ELanguage;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.services.helpers.GenerationHelpers;
import fr.zhykos.ymodel.infrastructure.services.helpers.ZipHelpers;

class YmodelServiceTests {

    @Test
    void generate() throws GenerationException, IOException, SemanticListException, SyntaxException {
        // Given
        final File ymlFile = new File("src/test/resources/metamodel01.yml");

        // Where
        final byte[] zipResult = new YmodelService().generateMetamodel(ymlFile, ELanguage.TYPESCRIPT);

        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        final Path directoryZipPath = fileSystem.getPath("zip");
        Files.createDirectory(directoryZipPath);
        final Path zipResultPath = Files.createTempFile(directoryZipPath, "result", ".zip");
        Files.write(zipResultPath, zipResult);
        final List<GeneratedFile> generatedFiles = ZipHelpers.unzip(zipResultPath);

        // Then
        Assertions.assertEquals(2, generatedFiles.size());

        final GeneratedFile generatedFile01 = generatedFiles.get(0);
        Assertions.assertEquals("Class01.ts", generatedFile01.getFilename());
        GenerationHelpers.assertStringEqualsFileContentsAsExcepted(generatedFile01.getContents(),
                "src/test/resources/expected-typescript/Class01.ts");

        final GeneratedFile generatedFile02 = generatedFiles.get(1);
        Assertions.assertEquals("Class02.ts", generatedFile02.getFilename());
        GenerationHelpers.assertStringEqualsFileContentsAsExcepted(generatedFile02.getContents(),
                "src/test/resources/expected-typescript/Class02.ts");
    }

}
