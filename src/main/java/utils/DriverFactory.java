package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class DriverFactory {
    @Step("Создание WebDriver для браузера")
    public static WebDriver createDriver(String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {

            WebDriverManager.chromedriver().clearResolutionCache().setup();
            return new ChromeDriver();
        } else if ("yandex".equalsIgnoreCase(browser)) {

            String yandexBinaryPath = "C:\\Users\\User\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";


            String yandexDriverPath = "C:\\Users\\User\\OneDrive\\Рабочий стол\\ЯндексБ\\yandexdriver.exe";
            System.setProperty("webdriver.chrome.driver", yandexDriverPath);

            ChromeOptions options = new ChromeOptions();
            options.setBinary(new File(yandexBinaryPath));

            return new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }
    }
}


