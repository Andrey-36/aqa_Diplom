package ru.netology.validationField;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckValidationFieldCardHolder {

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
    public void shouldEnterValidLatinName() {
        Page.fieldCardHolder.setValue("ANDREY K");
        String actualContentsField = Page.fieldCardHolder.getValue();
        assertEquals("ANDREY K", actualContentsField);
    }

    @Test
    public void shouldEnterCyrillicName() {
        Page.fieldCardHolder.setValue("андрей к");
        String actualContentsField = Page.fieldCardHolder.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterNumbers() {
        Page.fieldCardHolder.setValue("12345");
        String actualContentsField = Page.fieldCardHolder.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldEnterSpecialSymbols() {
        Page.fieldCardHolder.setValue("~!@#$%");
        String actualContentsField = Page.fieldCardHolder.getValue();
        assertEquals("", actualContentsField);
    }

    @Test
    public void shouldSendFormNameWithoutSpace() {
        Page.fieldCardHolder.setValue("ANDREYK");
        Page.fieldCardNumber.setValue(DataHelper.getApprovedCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getApprovedCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getApprovedCard().getYearCard());
        Page.fieldCvC.setValue(DataHelper.getApprovedCard().getCvcCard());
        Page.buttonNext.click();
        Page.notificationTitleError.should(appear, Duration.ofSeconds(100));
        Page.notificationTitleError.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
        Page.notificationContentError.shouldBe(visible).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }
}