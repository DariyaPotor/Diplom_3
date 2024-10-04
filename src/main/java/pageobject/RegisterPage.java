package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final By nameField = By.xpath(".//label[text() = 'Имя']/parent::div/input");//поле Имя
    private final By emailField = By.xpath(".//label[text() = 'Email']/parent::div/input"); //поле Email
    private final By passwordField = By.xpath(".//input[@name = 'Пароль']"); //поле Пароль
    private final By registerButton = By.xpath(".//*[text() = 'Зарегистрироваться']");//кнопка "Зарегистрироваться"
    private final By error = By.xpath(".//fieldset[3]/div/p[contains(text(), 'Некорректный пароль')]"); //ошибка
    private final By authLinkLogin = By.xpath(".//a[text() = 'Войти']"); //кнопка "Войти"

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы с регистрацией")
    public void openRegisterPage() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
    }

    @Step("Создание пользователя")
    public void createUser(String name, String email, String password) {
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(registerButton));
        driver.findElement(registerButton).click();
    }

    @Step("Выполнение входа по кнопке <Войти> в форме регистрации")
    public void clickAuthLinkLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(authLinkLogin));
        driver.findElement(authLinkLogin).click();
    }

    @Step("Ошибка для некорректного пароля. Минимальный пароль — шесть символов")
    public void getError() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(error));
        driver.findElement(error).isDisplayed();
    }

    // текст ошибки
    @Step("Проверить текст ошибки для некорректного пароля")
    public String getTextErrorMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(error));
        return driver.findElement(error).getText();
    }
}