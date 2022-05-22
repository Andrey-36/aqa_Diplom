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

public class CheckValidationFieldYear {

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
    public void shouldEnterValidYear() {
        Page.fieldYear.setValue("22");
        String actualContentsField = Page.fieldYear.getValue();
        assertEquals("22", actualContentsField);
    }

    @Test
    public void shouldEnterYearMore2() {
        Page.fieldYear.setValue("221");
        String actualContentsField = Page.fieldYear.getValue();
        assertEquals("22", actualContentsField);
    }

   @Test
    public void shouldEnterYearLatinLetters() {
        Page.fieldYear.setValue("ab");
        String actualContentsField = Page.fieldYear.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterYearCyrillicLetters() {
        Page.fieldYear.setValue("вг");
        String actualContentsField = Page.fieldYear.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterYearSpecialSymbols() {
        Page.fieldYear.setValue("~!");
        String actualContentsField = Page.fieldYear.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldSendFormYearIncomplete() {
        Page.fieldCardNumber.setValue(DataHelper.getApprovedCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getApprovedCard().getMonthCard());
        Page.fieldYear.setValue("2");
        Page.fieldCardHolder.setValue(DataHelper.getApprovedCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getApprovedCard().getCvcCard());
        Page.buttonNext.click();
        Page.invalidYearFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
    }
}