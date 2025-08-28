package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.example.pages.MainPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqTest {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testFaqAccordion() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.scrollToFaq();

        for (int i = 0; i < 8; i++) {
            final int index = i; // 🔥 делаем effectively final

            mainPage.clickFaqQuestion(index);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertTrue(mainPage.isFaqAnswerDisplayed(index),
                    "Ответ на вопрос " + (index + 1) + " не отображается после клика");
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}