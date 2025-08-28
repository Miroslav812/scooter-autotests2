package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Локаторы формы заказа ----------
    private final By firstName = By.xpath("//input[@placeholder='* Имя']");
    private final By lastName = By.xpath("//input[@placeholder='* Фамилия']");
    private final By address = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStation = By.cssSelector(".select-search__input"); 
    private final By phone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    private final By nextButton = By.xpath("//button[text()='Далее']");

    private final By deliveryDate = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.cssSelector(".Dropdown-control");

    private final By blackColor = By.id("black");
    private final By greyColor = By.id("grey");

    private final By orderButton = By.xpath("//button[contains(text(),'Заказать')]");
    private final By confirmYes = By.xpath("//button[text()='Да']");

    private final By successModal = By.cssSelector(".Order_Modal__YZ-d3");

    // ---------- Методы ----------
    public void fillFirstStep(String name, String surname, String addr, String metro, String phoneNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(name);
        driver.findElement(lastName).sendKeys(surname);
        driver.findElement(address).sendKeys(addr);
        // Станция метро (ввод и Enter для выбора первого совпадения)
        WebElement metroInput = driver.findElement(metroStation);
        metroInput.click();
        metroInput.sendKeys(metro);
        metroInput.sendKeys(Keys.ENTER);

        driver.findElement(phone).sendKeys(phoneNumber);
        driver.findElement(nextButton).click();
    }

    public void fillSecondStep(String date, String period, String color) {
        wait.until(ExpectedConditions.elementToBeClickable(deliveryDate)).click();
        driver.findElement(deliveryDate).sendKeys(date);
        driver.findElement(deliveryDate).sendKeys(Keys.ENTER);

        driver.findElement(rentalPeriod).click();
        driver.findElement(By.xpath("//div[contains(@class,'Dropdown-menu')]//div[text()='" + period + "']")).click();

        if ("black".equalsIgnoreCase(color)) driver.findElement(blackColor).click();
        if ("grey".equalsIgnoreCase(color)) driver.findElement(greyColor).click();

        driver.findElement(orderButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYes)).click();
    }

    public boolean isSuccessModalShown() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successModal)).isDisplayed();
    }
}
