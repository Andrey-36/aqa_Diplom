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

public class CheckValidationFieldCvc {

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
    public void shouldEnterValidCvСNumber() {
        Page.fieldCvC.setValue("897");
        String actualContentsField = Page.fieldCvC.getValue();
        assertEquals("897", actualContentsField);
    }

    @Test
    public void shouldEnterCvCNumberMore3() {
        Page.fieldCvC.setValue("8975");
        String actualContentsField = Page.fieldCvC.getValue();
        assertEquals("897", actualContentsField);
    }

    @Test
    public void shouldEnterCvCNumberLatinLetters() {
        Page.fieldCvC.setValue("abc");
        String actualContentsField = Page.fieldCvC.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterCvCNumberCyrillicLetters() {
        Page.fieldCvC.setValue("абв");
        String actualContentsField = Page.fieldCvC.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterCvCNumberSpecialSymbols() {
        Page.fieldCvC.setValue("~!@");
        String actualContentsField = Page.fieldCvC.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldSendFormCvcIncomplete() {
        Page.fieldCvC.setValue("8");
        Page.fieldMonth.setValue(DataHelper.getApprovedCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getApprovedCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getApprovedCard().getCardHolder());
        Page.buttonNext.click();
        Page.invalidCvCFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
    }
}