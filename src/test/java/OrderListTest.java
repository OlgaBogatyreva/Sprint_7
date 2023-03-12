import client.OrderSteps;
import model.orders.list.OrdersList;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class OrderListTest {
    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @Test
    public void getOrdersListMustBeOk() {
        OrdersList ordersList = orderSteps.get();
        MatcherAssert.assertThat(ordersList, notNullValue());
    }
}
