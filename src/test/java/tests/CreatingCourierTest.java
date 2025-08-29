package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static steps.Steps.*;

public class CreatingCourierTest extends Predok {

    @Test
    @DisplayName("Позитивный тест создания курьера")
    @Description("Это проверка успешного запроса создания курьера")
    public void courierCreatingPozitive() {
        String login = "Klinikov4";
        String password = "10458617";
        String firstName = "Andrey";
        Response response = courierCreate(login, password, firstName);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        Response responseLogin = login(login, password);
        int id = responseLogin.then().extract().body().path("id");
        deleteCourier(id);
    }

    @Test
    @DisplayName("Тест создания двух одинаковых курьеров")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingCourierPovtor() {
        String login = "Klinikov4";
        String password = "10458617";
        String firstName = "Andrey";
        courierCreate(login, password, firstName);
        Response responsePovtor = courierCreate(login, password, firstName);
        responsePovtor.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        Response responseLogin = login(login, password);
        int id = responseLogin.then().extract().body().path("id");
        deleteCourier(id);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingCourierBezLogina() {
        String login = "";
        String password = "10458617";
        String firstName = "Andrey";
        Response response = courierCreate(login, password, firstName);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingCourierBezParolya() {
        String login = "Klinikov7";
        String password = "";
        String firstName = "Andrey";
        Response response = courierCreate(login, password, firstName);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}
