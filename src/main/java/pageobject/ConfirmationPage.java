package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {
    private final WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By successModal = By.cssSelector(".Order_Modal__YZ-d3");

    public boolean isOrderConfirmed() {
        return driver.findElement(successModal).isDisplayed();
    }
}
