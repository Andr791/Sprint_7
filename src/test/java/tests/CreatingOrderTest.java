package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;
import static steps.Steps.createOrder;

@RunWith(Parameterized.class)
public class CreatingOrderTest extends Predok {
    private String[] color;

    public CreatingOrderTest(String[] color) {
        this.color = color;
    }

    @Test
    @DisplayName("Тест создания нового заказа")
    @Description("Тут проверяется создание заказа с разными вариантами цветов")
    public void createNewOrder() {
        Response response = createOrder(color);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }

}
