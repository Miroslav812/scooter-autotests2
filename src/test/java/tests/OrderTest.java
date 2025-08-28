package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.ConfirmationPage;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest extends BaseTest {

    @ParameterizedTest(name = "Оформление заказа: {0} {1}, кнопка: {8}")
    @CsvSource({
            "Иван, Иванов, Москва, Пушкинская, +79998887766, 25.08.2025, сутки, black, top",
            "Мария, Смирнова, СПб, Невский, +79991112233, 26.08.2025, трое суток, grey, bottom"
    })
    void makeOrder(String name, String surname, String address, String metro, String phone,
                   String date, String period, String color, String button) {

        MainPage mainPage = new MainPage(driver);

        // Ждем, пока верхняя кнопка "Заказать" станет видимой (если нужно)
        mainPage.waitForTopOrderButtonVisible();

        // Клик по нужной кнопке заказа с закрытием баннера cookies
        if ("top".equalsIgnoreCase(button)) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        // Заполняем первый шаг заказа
        orderPage.fillFirstStep(name, surname, address, metro, phone);

        // Заполняем второй шаг заказа
        orderPage.fillSecondStep(date, period, color);

        // Подтверждение заказа
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        boolean confirmed;

        try {
            // Ждем до 10 секунд появления окна подтверждения
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> confirmationPage.isOrderConfirmed());
            confirmed = true;
        } catch (Exception e) {
            confirmed = false;
        }

        assertTrue(confirmed, "Заказ не был подтверждён (возможен известный баг в Chrome).");
    }
}