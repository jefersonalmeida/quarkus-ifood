package com.github.jefersonalmeida.ifood.register;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

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

}
