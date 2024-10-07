import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RecoveryPasswordPage;
import pageobject.RegisterPage;
import pojo.UserData;
import apisteps.UserApi;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginPageUserTest {

    private UserApi userApi;
    public String userEmail = "dddddeddaas@yandex.ru";
    public String userName = "Magomed";
    public String userPassword = "abobafederal";
    public String userAccessToken;
    private RegisterPage objRegisterPage;
    private LoginPage objLoginPage;
    private WebDriver driver;

    @Before
    public void setUp() {
        RestAssured.baseURI = UserApi.baseUrl;
        RestAssured.filters(new AllureRestAssured());
        userApi = new UserApi();
        UserData userData = new UserData(userEmail, userPassword, userName);
        userApi.createUser(userData);
        UserData userDataLogin = new UserData(userEmail, userPassword, null);
        Response response = userApi.loginUser(userDataLogin);
        userAccessToken = response.then().extract().path("accessToken");
        driver = WebDriverFactory.createWebDriver();
        objRegisterPage = new RegisterPage(driver);
    }

    @After
    public void tearDown() {
        if (userAccessToken != null && !userAccessToken.isEmpty()) {
            userApi.deleteUser(userAccessToken);
        }
        driver.quit();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    public void mainPageTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkAuthorization();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        assertEquals("Ошибка", "Войти", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void personalAccountTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        assertEquals("Ошибка", "Войти", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void registrationLinkTest() {
        objRegisterPage.openRegisterPage();
        objRegisterPage.clickAuthLinkLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        MainPage objMainPage = new MainPage(driver);
        assertEquals("Ошибка", "Войти", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void restorePasswordLinkTest() {
        RecoveryPasswordPage objRecoveryPasswordPage = new RecoveryPasswordPage(driver);
        objRecoveryPasswordPage.openRestorePage();
        objRecoveryPasswordPage.clickForgotPassword();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        MainPage objMainPage = new MainPage(driver);
        assertEquals("Ошибка", "Войти", objMainPage.checkOrderButton());
    }
}
