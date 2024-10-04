import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.RecoveryPasswordPage;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import pageobject.MainPage;
import static org.junit.Assert.assertEquals;

public class LoginPageUserTest {
    private RegisterPage objRegisterPage;
    private LoginPage objLoginPage;
    private WebDriver driver;
    private String userEmail;
    private String userPassword;
    String accessToken;

    @Before
    @DisplayName("Открытие браузера, сайта и создание данных тестового пользователя")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        UserData userData = new UserData();
        String userName = userData.getRandomName();
        userEmail = userData.getRandomEmail();
        userPassword = userData.getRandomPassword();
        objRegisterPage = new RegisterPage(driver);
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(userName, userEmail, userPassword);
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
    @After
    @DisplayName("Закрытие браузера")
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}