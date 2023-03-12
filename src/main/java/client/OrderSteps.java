package client;

import client.base.ScooterRestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.orders.list.OrdersList;

import static io.restassured.RestAssured.given;

public class OrderSteps extends ScooterRestClient {

    private static final String ORDER_URI = BASE_URI + "/orders";

    @Step("Create order {order}")
    public ValidatableResponse create(Order order) {
        return given()
                .body(order)
                .when()
                .post(ORDER_URI)
                .then();
    }

    @Step("Delete order {order}")
    public ValidatableResponse delete(int id) {
        return given()
                .when()
                .post(ORDER_URI + id)
                .then();
    }

    @Step("Get orders list")
    public OrdersList get() {
        return given()
                .header("Content-type", "application/json")
                .get(ORDER_URI)
                .body()
                .as(OrdersList.class);
    }
}
