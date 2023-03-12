package client;

import client.base.ScooterRestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierSteps extends ScooterRestClient {

    private static final String COURIER_URI = BASE_URI + "/courier";

    @Step("Create courier {cuorier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseReqSpec())
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then();
    }

    @Step("Login as {courier}")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_URI + "/login")
                .then();
    }

    @Step("Delete courier {id}")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .delete(COURIER_URI + id)
                .then();
    }
}
