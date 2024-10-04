import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserData {
    private final String userEmail = "dasha" + new Random().nextInt(10000) + "@yandex.ru";
    private final String userPassword = "180603" + new Random().nextInt(10000);
    private final String userName = "dasha" + new Random().nextInt(10000);

    public String getRandomName() {// геттер для рандомного имени
        return this.userName;
    }

    public  String getRandomEmail() {//геттер для рандомного емайл
        return this.userEmail;
    }

    public  String getRandomPassword() {//геттер
        return this.userPassword;
    }

    public static RequestSpecification baseSpecification(){

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static final String USER = "/api/auth/user";
    @Step("Удаление пользователя.")
    public static void deleteUser(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization", accessToken)
                .spec(baseSpecification())
                .when()
                .delete(USER)
                .then();

    }
}