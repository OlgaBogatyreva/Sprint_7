import client.CourierSteps;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import model.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierLoginTests {
    private CourierSteps courierSteps;
    private int courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
    }

    @After
    public void clearData() {
        courierSteps.delete(courierId);
    }

    @Test
    public void courierCanAutorizeWithValidData() {
        Courier courier = CourierGenerator.getRandom();
        courierSteps.create(courier);
        ValidatableResponse loginResponce = courierSteps.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponce.extract().statusCode();
        courierId = loginResponce.extract().path("id");

        assertEquals("Status code is not correct", 200, statusCode);
        assertTrue("Courier id is not autorized", courierId != 0);
    }

    @Test
    public void courierCanNotAutorizeWithoutLogin() {
        Courier courier = CourierGenerator.getRandom();
        courierSteps.create(courier);
        ValidatableResponse loginResponce = courierSteps.login(new CourierCredentials(null, courier.getPassword()));
        int statusCode = loginResponce.extract().statusCode();
        String errorMsg = loginResponce.extract().path("message");
        courierId = loginResponce.extract().path("id");

        assertEquals("Status code is not correct", 400, statusCode);
        assertEquals("There is no correct error message", "Недостаточно данных для входа", errorMsg);
    }

    @Test
    public void courierCanAutorizeWithUnknownLoginPwd() {
        Courier courier = CourierGenerator.getRandom();
        courierSteps.create(courier);
        ValidatableResponse loginResponce = courierSteps.login(new CourierCredentials(courier.getLogin() + "unknown", courier.getPassword() + "unknown"));

        int statusCode = loginResponce.extract().statusCode();
        String errorMsg = loginResponce.extract().path("message");
        courierId = loginResponce.extract().path("id");

        assertEquals("Status code is not correct", 404, statusCode);
        assertEquals("There is no correct error message", "Учетная запись не найдена", errorMsg);
    }
}
