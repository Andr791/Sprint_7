import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void loginCourierPozitive() {
        TestDataCourier courier = new TestDataCourier("Klinikov4", "10458617", "Andrey");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        TestDataLogin login = new TestDataLogin(courier.getLogin(), courier.getPassword());
        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        responseLogin.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        int id = responseLogin.then().extract().body().path("id");

        Response responseDelete =
                given()
                        .delete("/api/v1/courier/{id}", id);
        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void loginCourierBezLogina() {
        TestDataLogin login = new TestDataLogin("", "10458617");
        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        responseLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void loginCourierBezParolya() {
        TestDataLogin login = new TestDataLogin("Klinikov4", "");
        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        responseLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void courierErrorLogin() {
        TestDataLogin login = new TestDataLogin("yuuyyuuy", "10458617");
        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        responseLogin.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void courierErrorPassword() {
        TestDataLogin login = new TestDataLogin("ninja", "10jtj");
        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        responseLogin.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
