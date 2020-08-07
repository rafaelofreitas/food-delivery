package br.com.fooddelivery;

import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.repository.KitchenRepository;
import br.com.fooddelivery.utils.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenControllerIT {
    private final static String BASE_PATH = "/kitchens";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Before
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = BASE_PATH;

        this.databaseCleaner.clearTables();
        this.prepareData();
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
    public void mustContain2Kitchens_WhenConsultingKitchen() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("content", hasSize(2));
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

    @Test
    public void mustReturnCorrectAnswerAndStatus_WhenConsultingExistingKitchen(){
        given()
                .pathParam("id", 2)
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Americana"));
    }

    @Test
    public void mustReturnStatus404_WhenConsultingNonexistentKitchen(){
        given()
                .pathParam("id", Integer.MAX_VALUE)
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen kitchen = Kitchen.builder()
                .name("Tailandesa")
                .build();

        this.kitchenRepository.save(kitchen);

        Kitchen kitchen2 = Kitchen.builder()
                .name("Americana")
                .build();

        this.kitchenRepository.save(kitchen2);
    }
}
