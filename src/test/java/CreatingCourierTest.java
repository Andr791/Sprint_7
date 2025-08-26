import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatingCourierTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void courierCreatingAndLoginPozitive() {
        TestDataCourier courier = courierCreate();
        int id = login(courier);
        deleteCourier(id);
    }

    @Test
    public void creatingCourierPovtor() {
        TestDataCourier courier = courierCreate();

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

        int id = login(courier);

        deleteCourier(id);
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

    //@Step("Создание курьера")
    public TestDataCourier courierCreate() {
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
        return courier;
    }

    //@Step("Залогиниться курьером")
    public int login(TestDataCourier courier) {
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
        return id;
    }

   // @Step("Удаление курьера")
    public void deleteCourier(int id) {
        Response responseDelete =
                given()
                        .delete("/api/v1/courier/{id}", id);
        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }
}
