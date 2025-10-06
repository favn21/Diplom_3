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

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordInput = By.xpath("//input[@type='password']");


    private By loginButton = By.xpath("//button[text()='Войти']");


    private By userProfile = By.xpath("//p[text()='Личный Кабинет']");


    private By loginFromHomeButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By loginFromPersonalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By loginFromRegistrationFormButton = By.xpath("//a[text()='Войти' and contains(@class,'Auth_link__1fOlj')]");
    private By loginFromRecoveryFormButton = By.xpath("//a[text()='Войти']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу")
    public void openMain() {
        driver.get("https://stellarburgers.nomoreparties.site/");
    }


    @Step("Нажать кнопку 'Войти в аккаунт' с главной страницы")
    public void clickLoginFromHome() {
        wait.until(ExpectedConditions.elementToBeClickable(loginFromHomeButton)).click();
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public void clickLoginFromPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(loginFromPersonalAccountButton)).click();
    }

    @Step("Нажать кнопку 'Войти' из формы восстановления пароля")
    public void clickLoginFromRecoveryForm() {
        wait.until(ExpectedConditions.elementToBeClickable(loginFromRecoveryFormButton)).click();
    }
    @Step("Авторизация пользователя с email")
    public void login(String email, String password) {
        setInput(emailInput, email);
        setInput(passwordInput, password);
        clickLoginButton();
    }

    private void setInput(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    private void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        new Actions(driver).moveToElement(button).perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }
    @Step("Нажать ссылку 'Войти' на форме регистрации")
    public void clickEnterButton() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(loginFromRegistrationFormButton));

        new Actions(driver).moveToElement(link).perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);

        try {
            link.click();
            System.out.println("Ссылка 'Войти' нажата обычным кликом");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            System.out.println("Ссылка 'Войти' нажата через JS");
        }
    }

    @Step("Проверить, что пользователь успешно авторизован")
    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userProfile));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
