package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//input[@type='password']");


    private By registerButton = By.xpath("//button[contains(text(),'Зарегистрироваться')]");


    private By errorMessage = By.cssSelector(".input__error, .error-message");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
    }


    public void register(String email, String password, String name) {
        setInput(nameField, name);
        setInput(emailField, email);
        setInput(passwordField, password);

        clickRegisterButton();
    }


    private void setInput(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }


    private void clickRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registerButton));


        new Actions(driver).moveToElement(button).perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    @Step("Проверить, что регистрация прошла успешно (редирект на страницу логина)")
    public boolean isRegistrationSuccessful() {
        try {
            wait.until(ExpectedConditions.urlContains("/login"));
            System.out.println("URL после регистрации: " + driver.getCurrentUrl());
            return driver.getCurrentUrl().contains("/login");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получить сообщение об ошибке регистрации")
    public String getErrorMessage() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.getText();
        } catch (Exception e) {
            return "";
        }
    }
}





