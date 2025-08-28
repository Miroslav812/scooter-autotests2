package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ---------- Элементы ----------
    private final By logo = By.cssSelector(".Header_LogoScooter__3lsAR");
    private final By topOrderButton = By.xpath("//div[contains(@class,'Header_Nav')]/button[text()='Заказать']");
    private final By bottomOrderButton = By.xpath("//div[contains(@class,'Home_FinishButton')]//button");
    private final By cookieBanner = By.cssSelector(".App_CookieText__1sbqp"); // пример
    private final By faqQuestions = By.cssSelector(".accordion__button");
    private final By faqAnswers = By.cssSelector(".accordion__panel");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Методы ----------

    // Закрыть баннер, если есть
    public void closeCookieBannerIfPresent() {
        try {
            WebElement banner = driver.findElement(cookieBanner);
            if (banner.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", banner);
            }
        } catch (NoSuchElementException ignored) {}
    }

    // Клик по логотипу
    public void clickLogo() {
        closeCookieBannerIfPresent();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(logo));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    // Клик по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        closeCookieBannerIfPresent();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(topOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    // Клик по нижней кнопке "Заказать"
    public void clickBottomOrderButton() {
        closeCookieBannerIfPresent();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    // Дожидаемся, пока верхняя кнопка "Заказать" станет видимой
    public void waitForTopOrderButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(topOrderButton));
    }

    // FAQ
    public List<WebElement> getFaqQuestions() {
        return driver.findElements(faqQuestions);
    }

    public List<WebElement> getFaqAnswers() {
        return driver.findElements(faqAnswers);
    }

    public void openFaqAnswer(int index) {
        getFaqQuestions().get(index).click();
    }

    public String getFaqAnswerText(int index) {
        return getFaqAnswers().get(index).getText();
    }
}
