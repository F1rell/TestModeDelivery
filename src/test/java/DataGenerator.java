import com.github.javafaker.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRegistration(RegistrationDto registrationDto) {
        given()
                .spec(requestSpec)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto user(String status) {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new RegistrationDto(login, password, status));
        return new RegistrationDto(login, password, status);
    }


    public static RegistrationDto newUserNoValidLogin() {
        String password = faker.internet().password();
        makeRegistration(new RegistrationDto("novalidlogin", password, "active"));
        return new RegistrationDto("login", password, "active");
    }

    public static RegistrationDto newUserNoValidPassword() {
        String login = faker.name().firstName().toLowerCase();
        makeRegistration(new RegistrationDto(login, "novalidpass", "active"));
        return new RegistrationDto(login, "novalid", "active");
    }
}