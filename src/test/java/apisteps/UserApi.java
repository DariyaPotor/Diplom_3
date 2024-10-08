package apisteps;

import pojo.UserData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private static final String USER_REGISTER_PATH = "api/auth/register";
    private static final String USER_DELETE_PATH = "api/auth/user";
    private static final String USER_LOGIN_PATH = "api/auth/login";

    UserData userData = new UserData();

    @Step("Регистрация пользователя.")
    public Response createUser(UserData userData) {
        return given()
                .contentType("application/json")
                .body(userData)
                .when()
                .post(USER_REGISTER_PATH);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String UserAccessToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", UserAccessToken)
                .when()
                .delete(USER_DELETE_PATH);
    }
    @Step("Авторизация пользователя.")
    public Response loginUser(UserData userData) {
        return given()
                .contentType("application/json")
                .body(userData)
                .when()
                .post(USER_LOGIN_PATH);
    }
}
