package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;

import org.junit.*;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;
import pages.LoginPage;
import utils.DriverFactory;
import utils.UserClient;

import static org.apache.http.HttpStatus.SC_ACCEPTED;

import static org.junit.Assert.assertTrue;


@Epic("Раздел «Конструктор»")
@Feature("Навигация по разделам конструктора")
public class ConstructorTests {

    private WebDriver driver;
    private ConstructorPage constructorPage;
    private LoginPage loginPage;

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
        constructorPage = new ConstructorPage(driver);


        constructorPage.openMainPage();
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
    @Story("Переход к разделу Булки")
    @DisplayName("Раздел 'Булки' виден после клика и скролла")
    @Description("Проверка, что раздел «Булки» виден после клика по вкладке и после скролла")
    public void testOpenBunsSection() {
        constructorPage.openBunsTab();
        assertTrue("Раздел 'Булки' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Булки"));

        constructorPage.scrollToSection("Булки");
        assertTrue("Раздел 'Булки' не виден после скролла",
                constructorPage.isSectionVisible("Булки"));
    }

    @Test
    @Story("Переход к разделу Начинки")
    @DisplayName("Раздел 'Начинки' виден после клика и скролла")
    @Description("Проверка, что раздел «Начинки» виден после клика по вкладке и после скролла")
    public void testOpenFillingsSection() {
        constructorPage.openFillingsTab();
        assertTrue("Раздел 'Начинки' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Начинки"));

        constructorPage.scrollToSection("Начинки");
        assertTrue("Раздел 'Начинки' не виден после скролла",
                constructorPage.isSectionVisible("Начинки"));
    }

    @Test
    @Story("Переход к разделу Соусы")
    @DisplayName("Раздел 'Соусы' виден после клика и скролла")
    @Description("Проверка, что раздел «Соусы» виден после клика по вкладке и после скролла")
    public void testOpenSaucesSection() {
        constructorPage.openSaucesTab();
        assertTrue("Раздел 'Соусы' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Соусы"));

        constructorPage.scrollToSection("Соусы");
        assertTrue("Раздел 'Соусы' не виден после скролла",
                constructorPage.isSectionVisible("Соусы"));
    }

    @Test
    @Story("Активный раздел конструктора по умолчанию")
    @DisplayName("По умолчанию активен раздел 'Булки'")
    @Description("Проверка, что при открытии страницы конструктор открывается с активным разделом 'Булки'")
    public void testDefaultActiveSection() {
        Assert.assertTrue(constructorPage.isSectionActive("Булки"));
    }
}












