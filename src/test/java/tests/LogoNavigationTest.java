package tests;

import org.junit.jupiter.api.Test;
import pageobject.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoNavigationTest extends BaseTest {

    @Test
    void clickLogoFromOrderAndBackToMain() {
        MainPage mainPage = new MainPage(driver);
        // Открыть форму заказа из шапки
        mainPage.clickTopOrderButton();
        // Вернуться на главную по клику на логотип
        mainPage.clickLogo();

        String url = driver.getCurrentUrl();
        assertTrue(url.contains("qa-scooter.praktikum-services.ru"), "После клика на логотип не вернулись на главную");
    }
}
