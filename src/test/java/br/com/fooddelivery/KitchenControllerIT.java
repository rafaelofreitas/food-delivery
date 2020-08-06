package br.com.fooddelivery;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KitchenControllerIT {
    private final static String BASE_PATH = "/kitchens";

    @LocalServerPort
    private int port;

    @Before
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void mustReturnStatus200_WhenConsultingKitchen() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustContain4Kitchens_WhenConsultingKitchen() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("content", hasSize(4));
    }

    @Test
    public void mustReturnStatus201_WhenInsertKitchen() {
        given()
                .body("{ \"name\": \"Kitchen\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
