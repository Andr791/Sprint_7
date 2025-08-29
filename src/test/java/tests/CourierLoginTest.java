package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.Steps.*;

public class CourierLoginTest extends Predok {

    @Test
    @DisplayName("Позитивный тест логина курьера") // имя теста
    @Description("Это проверка успешного логина курьера в системе") // описание теста
    public void courierLoginPozitive() {
        String login = "Klinikov4";
        String password = "10458617";
        String firstName = "Andrey";
        courierCreate(login, password, firstName);

        Response responseLogin = login(login, password);
        responseLogin.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        int id = responseLogin.then().extract().body().path("id");

        deleteCourier(id);
    }

    @Test
    @DisplayName("Залогиниться без ввода логина") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void loginCourierBezLogina() {
        String login = "";
        String password = "10458617";

        Response responseLogin = login(login, password);
        responseLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Залогиниться без ввода пароля") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void loginCourierBezParolya() {
        String login = "Klinikov4";
        String password = "";

        Response responseLogin = login(login, password);
        responseLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизоваться под несуществующим пользователем") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void courierErrorLogin() {
        String login = "yuuyyuuy";
        String password = "10458617";

        Response responseLogin = login(login, password);
        responseLogin.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Неправильно указать пароль") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void courierErrorPassword() {
        String login = "ninja";
        String password = "10jtj";

        Response responseLogin = login(login, password);
        responseLogin.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
