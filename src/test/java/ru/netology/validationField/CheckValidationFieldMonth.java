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

public class CheckValidationFieldMonth {

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
    public void shouldEnterValidNumberMonth() {
        Page.fieldMonth.setValue("07");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("07", actualContentsField);
    }

    @Test
    public void shouldEnterNumberMonthMore2() {
        Page.fieldMonth.setValue("071");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("07", actualContentsField);
    }

    @Test
    public void shouldEnterNumberMonthAboveRange() {
        Page.fieldMonth.setValue("13");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterLatinNumberMonth() {
        Page.fieldMonth.setValue("ab");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterCyrillicNumberMonth() {
        Page.fieldMonth.setValue("вг");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterSpecialSymbolsNumberMonth() {
        Page.fieldMonth.setValue("~!");
        String actualContentsField = Page.fieldMonth.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldSendFormMonthIncomplete() {
        Page.fieldCardNumber.setValue(DataHelper.getApprovedCard().getNumberCard());
        Page.fieldYear.setValue(DataHelper.getApprovedCard().getYearCard());
        Page.fieldMonth.setValue("0");
        Page.fieldCardHolder.setValue(DataHelper.getApprovedCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getApprovedCard().getCvcCard());
        Page.buttonNext.click();
        Page.invalidMonthFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
    }
}