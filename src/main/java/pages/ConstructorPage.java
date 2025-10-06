package pages;

import org.openqa.selenium.*;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By bunsTab = By.xpath("//span[text()='Булки']");
    private By saucesTab = By.xpath("//span[text()='Соусы']");
    private By fillingsTab = By.xpath("//span[text()='Начинки']");

    private By bunsSection = By.xpath("//h2[text()='Булки']");
    private By saucesSection = By.xpath("//h2[text()='Соусы']");
    private By fillingsSection = By.xpath("//h2[text()='Начинки']");


    private final String activeTabTemplate = "//span[text()='%s']/parent::div[contains(@class,'tab_tab__')]";

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть раздел Булки")
    public void openBunsTab() {
        clickTab(bunsTab, bunsSection);
    }

    @Step("Открыть раздел Соусы")
    public void openSaucesTab() {
        clickTab(saucesTab, saucesSection);
    }

    @Step("Открыть раздел Начинки")
    public void openFillingsTab() {
        clickTab(fillingsTab, fillingsSection);
    }

    @Step("Клик по вкладке")
    private void clickTab(By tab, By section) {
        WebElement tabElement = wait.until(ExpectedConditions.elementToBeClickable(tab));


        new Actions(driver).moveToElement(tabElement).perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabElement);


        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabElement);


        wait.until(ExpectedConditions.visibilityOfElementLocated(section));
    }
    @Step("Проскроллить к разделу")
    public void scrollToSection(String sectionName) {
        By sectionLocator = getSectionLocator(sectionName);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(sectionLocator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'start'});", section
        );
    }
    @Step("Проверить, что раздел отображается")
    public boolean isSectionVisible(String sectionName) {
        By sectionLocator = getSectionLocator(sectionName);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(sectionLocator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private By getSectionLocator(String sectionName) {
        switch (sectionName) {
            case "Булки": return bunsSection;
            case "Соусы": return saucesSection;
            case "Начинки": return fillingsSection;
            default: throw new IllegalArgumentException("Неизвестный раздел: " + sectionName);
        }
    }

    @Step("Проверить, что раздел активен")
    public boolean isSectionActive(String sectionName) {
        By activeTabLocator = By.xpath(String.format(activeTabTemplate, sectionName));
        WebElement sectionTab = driver.findElement(activeTabLocator);
        return sectionTab.getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }

    @Step("Открыть главную страницу конструктора")
    public void openMainPage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
    }
}







