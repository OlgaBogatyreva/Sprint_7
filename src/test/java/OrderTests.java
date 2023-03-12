import client.OrderSteps;
import io.restassured.response.ValidatableResponse;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests {
    private OrderSteps orderSteps;
    private int orderId;

    private final String[] color;

    public OrderTests(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @After
    public void clearData() {
        orderSteps.delete(orderId);
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][]{
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY", "BLACK"}},
                {null},
        };
    }

    @Test
    public void orderCanBeCreatedWithValidData() {
        Order order = OrderGenerator.getRandom(color);
        ValidatableResponse createResponse = orderSteps.create(order);

        int statusCode = createResponse.extract().statusCode();
        orderId = createResponse.extract().path("track");

        assertEquals("Status code is not correct", 201, statusCode);
        assertTrue("Order id is not created", orderId != 0);
    }
}