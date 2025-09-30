package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import pages.ConstructorPage;
import utils.DriverFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@Epic("Раздел «Конструктор»")
@Feature("Навигация по разделам конструктора")
public class ConstructorTests {

    private final String browser;
    private WebDriver driver;
    private ConstructorPage constructorPage;


    public ConstructorTests(String browser) {
        this.browser = browser;
    }


    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void setup() {
        driver = DriverFactory.createDriver(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://stellarburgers.nomoreparties.site");
        constructorPage = new ConstructorPage(driver);
    }

    @After
    public void teardown() {
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
        Assert.assertTrue("Раздел 'Булки' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Булки"));


        constructorPage.scrollToSection("Булки");
        Assert.assertTrue("Раздел 'Булки' не виден после скролла",
                constructorPage.isSectionVisible("Булки"));
    }

    @Test
    @Story("Переход к разделу Начинки")
    @DisplayName("Раздел 'Начинки' виден после клика и скролла")
    @Description("Проверка, что раздел «Начинки» виден после клика по вкладке и после скролла")
    public void testOpenFillingsSection() {

        constructorPage.openFillingsTab();
        Assert.assertTrue("Раздел 'Начинки' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Начинки"));


        constructorPage.scrollToSection("Начинки");
        Assert.assertTrue("Раздел 'Начинки' не виден после скролла",
                constructorPage.isSectionVisible("Начинки"));
    }
    @Test
    @Story("Переход к разделу Соусы")
    @DisplayName("Раздел 'Соусы' виден после клика и скролла")
    @Description("Проверка, что раздел «Соусы» виден после клика по вкладке и после скролла")
    public void testOpenSaucesSection() {

        constructorPage.openSaucesTab();
        Assert.assertTrue("Раздел 'Соусы' не виден после клика по вкладке",
                constructorPage.isSectionVisible("Соусы"));


        constructorPage.scrollToSection("Соусы");
        Assert.assertTrue("Раздел 'Соусы' не виден после скролла",
                constructorPage.isSectionVisible("Соусы"));
    }

}










