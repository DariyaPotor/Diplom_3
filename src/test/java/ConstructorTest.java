import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {
    private MainPage objMainPage;
    private WebDriver driver;

    @Before
    @DisplayName("Открытие браузера и сайта")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверка перехода к разделу с соусами")
    public void checkSauce() {
        objMainPage.openMainPage();
        assertTrue("Error", objMainPage.checkSauce());
    }

    @Test
    @DisplayName("Проверка перехода к разделу с булками")
    public void checkBuns() {
        objMainPage.openMainPage();
        assertTrue("Error", objMainPage.checkBuns());
    }

    @Test
    @DisplayName("Проверка перехода к разделу с начинками")
    public void checkFillings() {
        objMainPage.openMainPage();
        assertTrue("Error", objMainPage.checkFillings());
    }

    @After
    @DisplayName("Закрытие браузера")
    public void teardown() {
        driver.quit();
    }
}