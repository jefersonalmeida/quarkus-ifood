package com.github.jefersonalmeida.ifood.register;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@DBRider
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycle.class)
public class RestaurantResourceTest {

    @Test
    @DataSet(value = "restaurants-test-1.yml")
    public void testSearchRestaurants() {
        String result = given()
                .when().get("/restaurants")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        Approvals.verifyJson(result);
    }

    private RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    @Test
    @DataSet(value = "restaurants-test-1.yml")
    public void testUpdateRestaurant() {
        Restaurant dto = new Restaurant();
        dto.name = "new Restaurant";
        UUID restaurantId = UUID.fromString("e7c9a541-58d1-485f-90d4-977ce4815f94");

        given()
                .with().pathParam("restaurantId", restaurantId)
                .body(dto)
                .when().put("/restaurants/{restaurantId}")
                .then()
                .statusCode(200)
                .extract().asString();

        Restaurant findById = Restaurant.findById(restaurantId);

        Assert.assertEquals(dto.name, findById.name);
    }

}
