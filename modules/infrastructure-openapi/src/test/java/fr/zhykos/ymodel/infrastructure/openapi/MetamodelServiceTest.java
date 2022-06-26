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
package fr.zhykos.ymodel.infrastructure.openapi;

import java.io.File;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
class MetamodelServiceTest {

    @Disabled
    @Test
    void generate() {
        final File ymlFile = new File("src/test/resources/metamodel01.yml");

        // RestAssured.given()
        // .when()
        // .contentType("multipart/form-data")
        // .multiPart("file", ymlFile, "multipart/form-data")
        // .formParam("language", "typescript")
        // .post("/metamodel/generate")
        // .then()
        // .statusCode(200);
        // .body(CoreMatchers.is("Hello Spring"));

        RestAssured
                .given()
                .contentType("multipart/form-data")
                .multiPart("file", ymlFile, "multipart/form-data")
                .formParam("language", "typescript")
                .expect().body("zip", CoreMatchers.is("OK"))
                .when().post("/metamodel/generate");
    }
}

/*
 * @QuarkusIntegrationTest
 * public class GreetingResourceIT extends GreetingResourceTest {
 * 
 * // Execute the same tests but in native mode.
 * }
 */