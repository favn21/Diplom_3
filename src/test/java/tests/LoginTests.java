package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@Epic("Авторизация")
@Feature("Вход в систему")
public class LoginTests {

    private final String browser;
    private WebDriver driver;
    private LoginPage loginPage;


    private final String validEmail = "us5237@example.com";
    private final String validPassword = "validPassword3";


    public LoginTests(String browser) {
        this.browser = browser;
    }


    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<String> browsers() {
        return Arrays.asList("chrome", "yandex");
    }

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @Story("Вход через кнопку 'Войти в аккаунт' на главной")
    @DisplayName("Успешный вход через главную страницу")
    @Description("Проверка, что пользователь может войти через главную страницу приложения")
    public void loginViaHomeButton() {
        loginPage.openMain();
        loginPage.clickLoginFromHome();
        loginPage.login(validEmail, validPassword);
        assertTrue("Пользователь не авторизовался через главную страницу", loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через кнопку 'Личный кабинет'")
    @DisplayName("Успешный вход через Личный кабинет")
    @Description("Проверка, что пользователь может войти через кнопку Личный кабинет")
    public void loginViaPersonalAccountButton() {
        loginPage.openMain();
        loginPage.clickLoginFromPersonalAccount();
        loginPage.login(validEmail, validPassword);
        assertTrue("Пользователь не авторизовался через Личный кабинет", loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через форму регистрации")
    @DisplayName("Успешный вход через форму регистрации")
    @Description("Проверка, что пользователь может войти через форму регистрации")
    public void loginViaRegistrationForm() {

        driver.get("https://stellarburgers.nomoreparties.site/register");


        loginPage.clickEnterButton();


        loginPage.login(validEmail, validPassword);


        assertTrue("Пользователь не авторизовался через форму регистрации", loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через форму восстановления пароля")
    @DisplayName("Успешный вход через форму восстановления пароля")
    @Description("Проверка, что пользователь может войти через форму восстановления пароля")
    public void loginViaRecoveryForm() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        loginPage.clickLoginFromRecoveryForm();
        loginPage.login(validEmail, validPassword);
        assertTrue("Пользователь не авторизовался через форму восстановления пароля", loginPage.isUserLoggedIn());
    }
}




