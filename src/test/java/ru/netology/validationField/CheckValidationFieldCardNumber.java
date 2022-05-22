package ru.netology.validationField;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.Page;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckValidationFieldCardNumber {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:8080/");
        Page.buttonBuy.click();
    }

    @Test
    public void shouldEnterValidCardNumber() {
        Page.fieldCardNumber.setValue("4444444444444441");
        String actualContentsField = Page.fieldCardNumber.getValue();
        assertEquals("4444 4444 4444 4441", actualContentsField);
    }

    @Test
    public void shouldEnterCardNumberMore16() {
        Page.fieldCardNumber.setValue("44444444444444414");
        String actualContentsField = Page.fieldCardNumber.getValue();
        assertEquals("4444 4444 4444 4441", actualContentsField);
    }

   @Test
    public void shouldEnterLatinLetters() {
        Page.fieldCardNumber.setValue("qwertyuiopasdfgh");
        String actualContentsField = Page.fieldCardNumber.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterCyrillicLetters() {
        Page.fieldCardNumber.setValue("йцукенгшщзфывапр");
        String actualContentsField = Page.fieldCardNumber.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterSpecialSymbols() {
        Page.fieldCardNumber.setValue("~!@#$%^&*()_+~!");
        String actualContentsField = Page.fieldCardNumber.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldSendFormNumberIncomplete() {
        Page.fieldCardNumber.setValue("44");
        Page.fieldMonth.setValue(DataHelper.getApprovedCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getApprovedCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getApprovedCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getApprovedCard().getCvcCard());
        Page.buttonNext.click();
        Page.invalidCardNumberFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
    }
}