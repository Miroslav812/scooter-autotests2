package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.example.pages.MainPage;
import com.example.pages.OrderPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver(); // Selenium Manager сам подберёт chromedriver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    private static Stream<Object[]> orderDataProvider() {
        return Stream.of(
                new Object[]{
                        "Иван", "Петров", "ул. Ленина, 1", "Сокольники", "+79123456789",
                        "01.01.2024", "сутки", "black", "Тестовый комментарий 1", true
                },
                new Object[]{
                        "Мария", "Сидорова", "пр. Мира, 25", "Черкизовская", "+79234567890",
                        "15.01.2024", "двое суток", "grey", "Тестовый комментарий 2", false
                }
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    public void testOrderCreation(
            String name, String surname, String address, String metro, String phone,
            String date, String period, String color, String comment, boolean topButton
    ) {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        OrderPage orderPage = mainPage.clickOrderButton(topButton);
        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.fillSecondForm(date, period, color, comment);
        orderPage.makeOrder();

        assertTrue(orderPage.isOrderSuccess(), "Заказ не был успешно создан");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
