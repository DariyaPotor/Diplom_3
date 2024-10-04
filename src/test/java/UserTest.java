import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.ProfilePage;
import pageobject.RegisterPage;
import pageobject.MainPage;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private LoginPage objLoginPage;
    private MainPage objMainPage;
    private WebDriver driver;
    private String email;
    private String password;
    String accessToken;

    @Before
    @DisplayName("Открытие браузера, сайта и создание данных тестового пользователя")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        UserData userData = new UserData();
        String name = userData.getRandomName();
        email = userData.getRandomEmail();
        password = userData.getRandomPassword();
        RegisterPage objRegisterPage = new RegisterPage(driver);
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(name,email,password);
        objLoginPage = new LoginPage(driver);
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверка на логин пользователя")
    public void personalAccountTest() {
        objLoginPage.login(email, password);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        assertEquals("Entering was  Failed", "Выход", objProfilePage.checkLogInPersonalAccount());
    }

    @Test
    @DisplayName("Проверка на выход из аккаунта")
    public void checkExitTest() {
        objLoginPage.login(email, password);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickExitButton();
        assertEquals("ExitFailed", "Войти", objLoginPage.checkLoginButton());
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void checkLogoTest() {
        objLoginPage.login(email, password);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickLogoButton();
        assertEquals("LogoButtonFailed", "Оформить заказ", objMainPage.checkOrderButton());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void checkConstructorTest() {
        objLoginPage.login(email, password);
        objMainPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickConstructorButton();
        assertEquals("ConstructorButtonFailed", "Оформить заказ", objMainPage.checkOrderButton());
    }
    @After
    @DisplayName("Закрытие браузера")
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}