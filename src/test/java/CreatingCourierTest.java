import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreatingCourierTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewPlaceAndCheckResponse() {
    /*    String json = "{\"login\": \"Klinikov2\", \"password\": \"10458617\", \"firstName\": \"Andrey\"}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);


        String json1 = "{\"login\": \"Klinikov2\", \"password\": \"10458617\"}";
        String photoId =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json1)
                        .when()
                        .post("/api/v1/courier/login").then().extract().body().path("id");
        //  response.then().assertThat().body("ok", equalTo(true))
        //        .and()
        //       .statusCode(200);
*/
        String json = "{\"id\": \"593169\"}";
        Response response2 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .delete("/api/v1/courier/:id");
        response2.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }
}
