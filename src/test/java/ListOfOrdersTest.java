import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void listOfOrders() {
        given()
                .get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
