import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void getMyInfoStatusCode() {
        given()
                .get("/api/users/me")
                .then().statusCode(200)
                .and()
                .assertThat().body("data.name", equalTo("здесь_укажи_имя_своего_пользователя"));
    }
}
