import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreatingCourierTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void creatingCourierPozitive() {
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
        int id =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login").then().extract().body().path("id");

        Response responseDelete =
                given()
                        .delete("/api/v1/courier/{id}", id);
        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void creatingCourierPovtor() {
        TestDataCourier courier = new TestDataCourier("Klinikov7", "10458617", "Andrey");
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

        Response responsePovtor =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        responsePovtor.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        TestDataLogin login = new TestDataLogin(courier.getLogin(), courier.getPassword());
        int id =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login").then().extract().body().path("id");

        Response responseDelete =
                given()
                        .delete("/api/v1/courier/{id}", id);
        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void creatingCourierBezLogina() {
        TestDataCourier courier = new TestDataCourier("", "10458617", "Andrey");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void creatingCourierBezParolya() {
        TestDataCourier courier = new TestDataCourier("Klinikov7", "", "Andrey");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}
