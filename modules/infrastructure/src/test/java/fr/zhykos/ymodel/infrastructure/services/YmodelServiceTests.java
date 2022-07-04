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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import fr.zhykos.ymodel.commons.ComparisonHelper;
import fr.zhykos.ymodel.commons.ZipHelper;
import fr.zhykos.ymodel.commons.models.ZipFile;
import fr.zhykos.ymodel.commons.models.comparison.ComparisonOK;
import fr.zhykos.ymodel.commons.models.comparison.IComparisonResult;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.services.GenerationService.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.ParsingService.SyntaxException;
import fr.zhykos.ymodel.infrastructure.services.TransformationService.SemanticListException;
import fr.zhykos.ymodel.infrastructure.services.ZipResultService.ZipException;

class YmodelServiceTests {

    @Test
    void generate() throws GenerationException, IOException, SemanticListException, SyntaxException, ZipException {
        // Given
        final File ymlFile = new File("src/test/resources/metamodel01.yml");

        // Where
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        new YmodelService().generateMetamodel(ymlFile, "typescript", byteStream);
        final byte[] zipResult = byteStream.toByteArray();

        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        final Path directoryZipPath = fileSystem.getPath("zip");
        Files.createDirectory(directoryZipPath);
        final Path zipResultPath = Files.createTempFile(directoryZipPath, "result", ".zip");
        Files.write(zipResultPath, zipResult);
        final List<ZipFile> zipFiles = ZipHelper.unzip(zipResultPath);
        final List<GeneratedFile> generatedFiles = zipFiles.stream()
                .map(zip -> new GeneratedFile(zip.getFilename(), zip.getContents())).toList();

        // Then
        Assertions.assertEquals(2, generatedFiles.size());

        final GeneratedFile generatedFile01 = generatedFiles.get(0);
        Assertions.assertEquals("Class01.ts", generatedFile01.getFilename());

        final IComparisonResult comparisonResult1 = ComparisonHelper.compareStringEqualsFileContentsAsExcepted(
                generatedFile01.getContents(), Path.of("src/test/resources/expected-typescript/Class01.ts"));

        MatcherAssert.assertThat(comparisonResult1, Matchers.instanceOf(ComparisonOK.class));

        final GeneratedFile generatedFile02 = generatedFiles.get(1);
        Assertions.assertEquals("Class02.ts", generatedFile02.getFilename());

        final IComparisonResult comparisonResult2 = ComparisonHelper.compareStringEqualsFileContentsAsExcepted(
                generatedFile02.getContents(), Path.of("src/test/resources/expected-typescript/Class02.ts"));

        MatcherAssert.assertThat(comparisonResult2, Matchers.instanceOf(ComparisonOK.class));
    }

}
