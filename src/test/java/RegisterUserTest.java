import apisteps.UserApi;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.UserData;
import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import pageobject.MainPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class RegisterUserTest {

    private RegisterPage objRegisterPage;
    private WebDriver driver;
    public String userAccessToken;
    public String userEmail = "dddffdfdlkjhgs@yandex.ru";
    public String userName = "Magfdfsdsfjhgfmed";
    public String userPassword = "abohfdgdsfdderall";
    private UserApi userApi;

    @Before
    @DisplayName("Открытие браузера, сайта и создание данных тестового пользователя")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        objRegisterPage = new RegisterPage(driver);
    }

    @Test
    @DisplayName("Регистрация пользователя и вход пользователя в систему")
    public void registrationUserTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(userName, userEmail, userPassword);
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        MainPage objMainPage = new MainPage(driver);
        assertEquals("Error", "Войти", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Проверка на ошибку")
    public void checkErrorMessageTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(userName, userEmail, "12345");
        objRegisterPage.getError();
        assertEquals("Error", "Некорректный пароль", objRegisterPage.getTextErrorMessage());
    }

    @After
    public void tearDown() {
        RestAssured.baseURI = UserApi.BASE_URI;
        RestAssured.filters(new AllureRestAssured());
        userApi = new UserApi();
        UserData userDataLogin = new UserData(userEmail, userPassword, null);
        Response response = userApi.loginUser(userDataLogin);
        userAccessToken = response.then().extract().path("accessToken");
        if (userAccessToken != null && !userAccessToken.isEmpty()) {
            userApi.deleteUser(userAccessToken);
        }
        driver.quit();
    }
}