package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;

import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.DriverFactory;
import utils.UserClient;

import static org.apache.http.HttpStatus.SC_ACCEPTED;

import static org.junit.Assert.assertTrue;

@Epic("Регистрация")
@Feature("Регистрация пользователей")
public class RegistrationTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;

    private String email;
    private String password;
    private String name;
    private String accessToken;

    @Before
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().setSize(new Dimension(1920, 1080));

        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
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
    @Story("Успешная регистрация")
    @DisplayName("Регистрация с валидными данными")
    @Description("Проверка успешной регистрации с валидными данными и редиректа на страницу логина")
    public void testSuccessfulRegistration() {
        registrationPage.open();
        String email = "us5237"+ System.currentTimeMillis()+"@example.com";
        String password = "validPassword3";
        String name = "User3"+ System.currentTimeMillis();
        registrationPage.register(email, password, name);
        assertTrue(registrationPage.isRegistrationSuccessful());
    }

    @Test
    @Story("Ошибка при регистрации")
    @DisplayName("Регистрация с коротким паролем")
    @Description("Проверка ошибки при регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        registrationPage.open();
        String email = "user7" + System.currentTimeMillis() + "@example.com";
        String shortPassword = "123";
        String name = "User7"+ System.currentTimeMillis();
        registrationPage.register(email, shortPassword, name);
        String error = registrationPage.getErrorMessage();
        assertTrue(error.contains("Некорректный пароль"));
    }
}






