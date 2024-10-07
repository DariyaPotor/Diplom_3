import apisteps.UserApi;
import io.restassured.response.Response;
import pojo.UserData;
import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.ProfilePage;
import pageobject.MainPage;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private WebDriver driver;
    public LoginPage objLoginPage;
    public String userEmail = "dddddeddaas@yandex.ru";
    public String userName = "Magomed";
    public String userPassword = "abobafederal";
    public UserApi userApi;
    public String userAccessToken;

    @Before
    @DisplayName("Открытие браузера, сайта и создание данных тестового пользователя")
    public void before() {
        RestAssured.baseURI = UserApi.baseUrl;
        RestAssured.filters(new AllureRestAssured());
        userApi = new UserApi();
        UserData userData = new UserData(userEmail, userPassword, userName);
        userApi.createUser(userData);
        UserData userDataLogin = new UserData(userEmail, userPassword, null);
        Response response = userApi.loginUser(userDataLogin);
        userAccessToken = response.then().extract().path("accessToken");
        objLoginPage = new LoginPage(driver);
        driver = WebDriverFactory.createWebDriver();
    }

    @Test
    @DisplayName("Проверка на логин пользователя")
    public void personalAccountTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        assertEquals("Entering was  Failed", "Выход", objProfilePage.checkLogInPersonalAccount());
    }

    @Test
    @DisplayName("Проверка на выход из аккаунта")
    public void checkExitTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickExitButton();
        assertEquals("ExitFailed", "Войти", objLoginPage.checkLoginButton());
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void checkLogoTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickLogoButton();
        assertEquals("LogoButtonFailed", "Оформить заказ", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void checkConstructorTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(userEmail, userPassword);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickConstructorButton();
        assertEquals("ConstructorButtonFailed", "Оформить заказ", objMainPage.checkOrderButton());
    }

    @After
    public void tearDown() {
        if (userAccessToken != null && !userAccessToken.isEmpty()) {
            userApi.deleteUser(userAccessToken);
        }
        driver.quit();
    }
}
