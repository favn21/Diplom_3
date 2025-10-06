package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;
import utils.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

@Epic("Авторизация")
@Feature("Вход в систему")
public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;

    private String email;
    private String password;
    private String name;
    private String accessToken;

    @Before
    public void setUp() {

        email = "testuser" + System.currentTimeMillis() + "@mail.ru";
        password = "Password123!";
        name = "TestUser" + System.currentTimeMillis();
        User user = new User(email, password, name);


        accessToken = UserClient.registerUser(user)
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");

        if (!accessToken.startsWith("Bearer ")) {
            accessToken = "Bearer " + accessToken;
        }


        driver = DriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().setSize(new Dimension(1920, 1080));
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {

        if (accessToken != null) {
            UserClient.deleteUser(accessToken).statusCode(SC_ACCEPTED);
        }
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
        loginPage.login(email, password);
        assertTrue(loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через кнопку 'Личный кабинет'")
    @DisplayName("Успешный вход через Личный кабинет")
    @Description("Проверка, что пользователь может войти через кнопку Личный кабинет")
    public void loginViaPersonalAccountButton() {
        loginPage.openMain();
        loginPage.clickLoginFromPersonalAccount();
        loginPage.login(email, password);
        assertTrue(loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через форму регистрации")
    @DisplayName("Успешный вход через форму регистрации")
    @Description("Проверка, что пользователь может войти через форму регистрации")
    public void loginViaRegistrationForm() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        loginPage.clickEnterButton();
        loginPage.login(email, password);
        assertTrue(loginPage.isUserLoggedIn());
    }

    @Test
    @Story("Вход через форму восстановления пароля")
    @DisplayName("Успешный вход через форму восстановления пароля")
    @Description("Проверка, что пользователь может войти через форму восстановления пароля")
    public void loginViaRecoveryForm() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        loginPage.clickLoginFromRecoveryForm();
        loginPage.login(email, password);
        assertTrue(loginPage.isUserLoggedIn());
    }
}








