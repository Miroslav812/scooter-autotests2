package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By orderButtons = By.xpath("//button[contains(text(), 'Заказать')]");
    private final By faqSection = By.className("Home_FAQ__3uVm4");
    private final By faqQuestions = By.xpath("//div[@class='accordion__button']");
    private final By faqAnswers = By.xpath("//div[@class='accordion__panel']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        acceptCookies();
    }

    private void acceptCookies() {
        try {
            wait.withTimeout(Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(cookieButton))
                    .click();
        } catch (Exception e) {
            System.out.println("Cookie button not found or not clickable");
        }
    }

    public OrderPage clickOrderButton(boolean topButton) {
        List<WebElement> buttons = driver.findElements(orderButtons);
        if (buttons.size() < 2) {
            throw new RuntimeException("Order buttons not found");
        }

        WebElement button = topButton ? buttons.get(0) : buttons.get(1);
        scrollAndClick(button);
        return new OrderPage(driver);
    }

    public void scrollToFaq() {
        WebElement faq = wait.until(ExpectedConditions.presenceOfElementLocated(faqSection));
        scrollIntoView(faq);
    }

    public void clickFaqQuestion(int index) {
        List<WebElement> questions = driver.findElements(faqQuestions);
        if (index < questions.size()) {
            scrollAndClick(questions.get(index));
        } else {
            throw new IllegalArgumentException("FAQ question with index " + index + " not found");
        }
    }

    public String getFaqAnswerText(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        if (index < answers.size()) {
            return answers.get(index).getText();
        }
        throw new IllegalArgumentException("FAQ answer with index " + index + " not found");
    }

    public boolean isFaqAnswerDisplayed(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        return index < answers.size() && answers.get(index).isDisplayed();
    }

    // Утилиты
    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void scrollAndClick(WebElement element) {
        scrollIntoView(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}