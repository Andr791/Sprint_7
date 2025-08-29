package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.TestDataCourier;
import pojo.TestDataLogin;
import pojo.TestDataOrder;

import static constante.Const.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Steps {
    @Step("Создание курьера")
    public static Response courierCreate(String login, String password, String firstName) {
        TestDataCourier courier = new TestDataCourier(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(RUKA_COURIER);
    }

    @Step("Залогиниться курьером")
    public static Response login(String login, String password) {
        TestDataLogin testDataLogin = new TestDataLogin(login, password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(testDataLogin)
                .when()
                .post(RUKA_LOGIN);
    }

    @Step("Удаление курьера")
    public static void deleteCourier(int id) {
        Response responseDelete =
                given()
                        .delete(RUKA_DELETE, id);
        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Step("Получить список заказов")
    public static Response listOrders() {
        return  given()
                .get(RUKA_ORDER);
    }

    @Step("Создать заказ")
    public static Response createOrder(String[] color) {
        TestDataOrder testDataOrder = new TestDataOrder("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", color);
        return given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(testDataOrder)
                        .when()
                        .post(RUKA_ORDER);
    }
}
