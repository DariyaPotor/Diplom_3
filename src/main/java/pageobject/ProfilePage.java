package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    private final WebDriver driver;
    private final By exitButton = By.className("Account_button__14Yp3"); //кнопка "Выход"
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']/a[@href='/' ]");  //логотип "Stellar burgers"
    private final By constructorButton = By.xpath("//a[@href='/' and @class='AppHeader_header__link__3D_hX']"); //кнопка "Конструктор"

    public ProfilePage(WebDriver driver) { //констуктор класса ProfilePage
        this.driver = driver;
    }

    @Step("Выход из личного кабинета по кнопке <Выход>")
    public void clickExitButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(exitButton));
        driver.findElement(exitButton).click();
    }

    @Step("Переход на стартовую страницу по логотипу <Stellar burgers>")
    public void clickLogoButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(logoButton));
        driver.findElement(logoButton).click();
    }

    @Step("Переход на стартовую страницу в раздел <Конструктор> по кнопке <Конструктор>")
    public void clickConstructorButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(constructorButton));
        driver.findElement(constructorButton).click();
    }

    @Step("Проверка кнопки <Выход>")
    public String checkLogInPersonalAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(exitButton));
        return driver.findElement(exitButton).getText();
    }
}