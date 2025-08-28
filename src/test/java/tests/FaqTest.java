package tests;

import org.junit.jupiter.api.Test;
import pageobject.MainPage;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class FaqTest extends BaseTest {

    @Test
    void checkFaqAnswersNotEmpty() {
        MainPage mainPage = new MainPage(driver);

        int size = mainPage.getFaqQuestions().size();
        for (int i = 0; i < size; i++) {
            mainPage.openFaqAnswer(i);
            String answer = mainPage.getFaqAnswerText(i);
            assertFalse(answer == null || answer.trim().isEmpty(), "Ответ на вопрос " + i + " пустой!");
        }
    }
}
