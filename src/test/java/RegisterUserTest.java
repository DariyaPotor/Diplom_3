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
    private String userName;
    private String userEmail;
    private String userPassword;
    String accessToken;

    @Before
    @DisplayName("Открытие браузера, сайта и создание данных тестового пользователя")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        UserData userData = new UserData();
        userName = userData.getRandomName();
        userEmail = userData.getRandomEmail();
        userPassword = userData.getRandomPassword();
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
    @DisplayName("Закрытие браузера")
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}