package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы первой формы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Локаторы второй формы
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.className("Dropdown-placeholder");
    private final By colorBlack = By.id("black");
    private final By colorGrey = By.id("grey");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle__1CSJM')]");
    private final By confirmButton = By.xpath("//button[text()='Да']");
    private final By successModal = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        waitAndType(nameField, name);
        waitAndType(surnameField, surname);
        waitAndType(addressField, address);

        // Выбор станции метро
        waitAndClick(metroField);
        WebElement metroStation = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'select-search__select')]//*[contains(text(), '" + metro + "')]")
        ));
        metroStation.click();

        waitAndType(phoneField, phone);
        waitAndClick(nextButton);
    }

    public void fillSecondForm(String date, String period, String color, String comment) {
        // Установка даты
        waitAndType(dateField, date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        // Выбор периода аренды
        waitAndClick(rentalPeriod);
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'Dropdown-menu')]//*[contains(text(), '" + period + "')]")
        ));
        periodOption.click();

        // Выбор цвета
        if ("black".equalsIgnoreCase(color)) {
            waitAndClick(colorBlack);
        } else if ("grey".equalsIgnoreCase(color)) {
            waitAndClick(colorGrey);
        }

        // Комментарий
        waitAndType(commentField, comment);
    }

    public void makeOrder() {
        waitAndClick(orderButton);
        waitAndClick(confirmButton);
    }

    public boolean isOrderSuccess() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Утилиты
    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void waitAndType(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
}