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
package fr.zhykos.ymodel.infrastructure.openapi.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.ZipHelper;
import fr.zhykos.ymodel.commons.models.ZipFile;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
class MetamodelServiceTest {

    @Test
    void generate() throws IOException {
        final File ymlFile = new File("src/test/resources/metamodel01.yml");
        final String response = RestAssured
                .given()
                .contentType("multipart/form-data")
                .multiPart("file", ymlFile, "multipart/form-data")
                .formParam("language", "typescript")
                .expect().statusCode(200)
                .when().post("/metamodel/generate")
                .andReturn().body().jsonPath().get("zip");

        final List<ZipFile> zipFiles = ZipHelper.unzip(Base64.getDecoder().decode(response));
        final List<GeneratedFile> generatedFiles = zipFiles.stream()
                .map(zip -> new GeneratedFile(zip.getFilename(), zip.getContents())).toList();

        Assertions.assertEquals(2, generatedFiles.size());

        final GeneratedFile generatedFile01 = generatedFiles.get(0);
        Assertions.assertEquals("Class01.ts", generatedFile01.getFilename());
        assertStringEqualsFileContentsAsExcepted(generatedFile01.getContents(),
                "src/test/resources/expected-typescript/Class01.ts");

        final GeneratedFile generatedFile02 = generatedFiles.get(1);
        Assertions.assertEquals("Class02.ts", generatedFile02.getFilename());
        assertStringEqualsFileContentsAsExcepted(generatedFile02.getContents(),
                "src/test/resources/expected-typescript/Class02.ts");
    }

    @Test
    void notFoundPage() {
        RestAssured
                .given()
                .expect().statusCode(404)
                .when().post("/metamodel/generating");
    }

    public static void assertStringEqualsFileContentsAsExcepted(final String actualString,
            final String expectedContentsFilepath) {
        try {
            final String expectedContents = Files.readString(Path.of(expectedContentsFilepath));
            Assertions.assertNotEquals("", actualString);
            Assertions.assertEquals(removeLineBreaks(expectedContents), removeLineBreaks(actualString));
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    private static String removeLineBreaks(final String string) {
        return string.replace("\r", "").replace("\n", "");
    }

}

/*
 * @QuarkusIntegrationTest
 * public class GreetingResourceIT extends GreetingResourceTest {
 * 
 * // Execute the same tests but in native mode.
 * }
 */