package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {

    @Disabled
    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/greeting")
                .then()
                .statusCode(200)
                .body(is("Hello Spring"));
    }

}