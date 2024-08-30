package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class WebTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @ParameterizedTest
    @CsvSource({
            "1366x768",
            "1440x900",
            "1024x768"
    })
    void testFilters(String resolution) throws InterruptedException {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(
                Integer.parseInt(resolution.split("x")[0]),
                Integer.parseInt(resolution.split("x")[1])
        ));

        driver.get("https://picsart.com/search/images/");
        TimeUnit.SECONDS.sleep(5);

        WebElement frameElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='com.picsart.social.search']")));
        WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-accept-btn-handler")));
        cookiesButton.click();

        driver.switchTo().frame(frameElement);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        openFilter();

        WebElement personalButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='licenses-Personal']")));
        personalButton.click();
        TimeUnit.SECONDS.sleep(5);
        closeFilter();

        assertTrue(driver.findElements(By.cssSelector("[data-testid='search-item-premium']")).isEmpty());

        TimeUnit.SECONDS.sleep(5);
        WebElement elementToHover = driver.findElement(By.id("base_card_item2"));
        actions.moveToElement(elementToHover).perform();

        WebElement likeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("like_button_item2")));
        actions.moveToElement(likeButton).click().perform();

        TimeUnit.SECONDS.sleep(5);

        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();

        WebElement modalCloseButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='modal-close-icon']")));

        actions.moveToElement(modalCloseButton).click().perform();

        driver.switchTo().frame(frameElement);

        openFilter();

        WebElement clearAllFiltersButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='search-filter-header-clear']")));
        TimeUnit.SECONDS.sleep(3);
        actions.moveToElement(clearAllFiltersButton).click().perform();
        TimeUnit.SECONDS.sleep(3);

        closeFilter();

        TimeUnit.SECONDS.sleep(3);

        actions.moveToElement(elementToHover).perform();

        WebElement tryNowButton = driver.findElement(By.cssSelector("[data-testid='try-now-button-root']"));
        actions.moveToElement(tryNowButton).click().perform();

        TimeUnit.SECONDS.sleep(20);
        WebElement popupCloseButton = driver.findElement(By.cssSelector("[data-testid='modal-close-icon']"));
        System.out.println(popupCloseButton.isDisplayed());
        actions.moveToElement(popupCloseButton).click().perform();

        TimeUnit.SECONDS.sleep(15);
    }

    private Actions actions;

    private void openFilter() {
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='search-header-filter']")));
        WebElement filterSection = driver.findElement(By.cssSelector("[data-testid='search-filter-root']"));

        if (!filterSection.isDisplayed()) {
            actions.moveToElement(filterButton).click().perform();
        }
    }

    private void closeFilter() {
        WebElement filterButton = driver.findElement(By.cssSelector("[data-testid='search-header-filter']"));
        WebElement filterSection = driver.findElement(By.cssSelector("[data-testid='search-filter-root']"));

        if (filterSection.isDisplayed()) {
            actions.moveToElement(filterButton).click().perform();
        }
    }

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1024x768");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
