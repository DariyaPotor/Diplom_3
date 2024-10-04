package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input"); //поле Email
    private final By passwordField = By.xpath("//input[@type='password']"); //поле Пароль
    private final By loginButton = By.xpath(".//button [text()='Войти']"); //кнопка "Войти"

    public LoginPage(WebDriver driver) {//конструктор класса LoginPage
        this.driver = driver;
    }

    @Step("Прохождение авторизации")
    public void login(String email, String password) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    @Step("Проверка на видимость кнопки <Войти>")
    public String checkLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        return driver.findElement(loginButton).getText();
    }
}