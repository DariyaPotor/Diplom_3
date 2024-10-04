package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoveryPasswordPage {

    private final WebDriver driver;
    private final static String PasswordRecoveryPage = "https://stellarburgers.nomoreparties.site/login";
    private final By restoreButton = By.xpath(".//a[text()='Восстановить пароль']"); //кнопка "Восстановить пароль"
    private final By loginButton = By.xpath(".//a[@class='Auth_link__1fOlj']"); //кнопка-ссылка "Войти" на странице "Восстановление пароля"

    public RecoveryPasswordPage(WebDriver driver) {// конструктор класса ForgotPasswordPage
        this.driver = driver;
    }

    @Step("Открытие страницы <Восстановление пароля>")
    public void openRestorePage() {
        driver.get(PasswordRecoveryPage);
    }

    @Step("Выполнение входа по кнопке <Войти> в форме восстановления пароля")
    public void clickForgotPassword() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(restoreButton));
        driver.findElement(restoreButton).click();
        driver.findElement(loginButton).click();
    }
}