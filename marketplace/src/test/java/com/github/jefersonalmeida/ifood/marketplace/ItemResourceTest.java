package com.github.jefersonalmeida.ifood.marketplace;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ItemResourceTest {

    @Test
    public void testItemsEndpoint() {
        String body = given()
                .when().get("/items")
                .then()
                .statusCode(200).extract().asString();
        System.out.println(body);
    }

}
