import client.CourierSteps;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import model.CourierGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreateTests {

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
    public void courierCanBeCreatedWithValidData() {
        Courier courier = CourierGenerator.getRandom();

        ValidatableResponse createResponse = courierSteps.create(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");

        assertEquals("Status code is incorrect", 201, statusCode);
        assertTrue("Courier is not created", isCourierCreated);

        ValidatableResponse loginResponse = courierSteps.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertTrue("Courier id is not created", courierId != 0);
    }

    @Test
    public void courierCanNotBeCreatedWithoutLogin() {
        Courier courier = new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse createResponse = courierSteps.create(courier);
        int statusCode = createResponse.extract().statusCode();
        String isCourierCreated = createResponse.extract().path("message");

        assertEquals("Status code is not correct", 400, statusCode);
        assertEquals("There is no correct error message", "Недостаточно данных для создания учетной записи", isCourierCreated);
    }

    @Test
    public void courierCanNotBeCreatedTwice() {
        Courier courier = CourierGenerator.getRandom();
        courierSteps.create(courier);
        ValidatableResponse createResponseTwice = courierSteps.create(courier);
        int statusCode = createResponseTwice.extract().statusCode();
        String isCourierCreatedTwice = createResponseTwice.extract().path("message");

        assertEquals("Status code is not correct", 409, statusCode);
        assertEquals("There is no correct error message", "Этот логин уже используется", isCourierCreatedTwice);
    }
}
