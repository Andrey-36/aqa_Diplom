package ru.netology.test;

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

public class BuyTourCreditCardTest {

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
        Page.buttonBuyCredit.click();
    }

    @Test
    public void shouldBuyTourApprovedCard() {
        Page.fieldCardNumber.setValue(DataHelper.getApprovedCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getApprovedCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getApprovedCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getApprovedCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getApprovedCard().getCvcCard());
        Page.buttonNext.click();
        Page.notificationTitleSuccess.should(appear, Duration.ofSeconds(100));
        Page.notificationTitleSuccess.shouldBe(visible).shouldHave(text("Успешно"));
        Page.notificationContentSuccess.shouldBe(visible).shouldHave(text("Операция одобрена Банком."));
    }

    @Test
    public void shouldBuyTourDeclinedCard() {
        Page.fieldCardNumber.setValue(DataHelper.getDeclinedCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getDeclinedCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getDeclinedCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getDeclinedCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getDeclinedCard().getCvcCard());
        Page.buttonNext.click();
        Page.notificationTitleError.should(appear, Duration.ofSeconds(100));
        Page.notificationTitleError.shouldBe(visible).shouldHave(text("Ошибка"));
        Page.notificationContentError.shouldBe(visible).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    public void shouldBuyTourWithCardOutsideTest() {
        Page.fieldCardNumber.setValue(DataHelper.getNotTestCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getNotTestCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getNotTestCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getNotTestCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getNotTestCard().getCvcCard());
        Page.buttonNext.click();
        Page.notificationTitleError.should(appear, Duration.ofSeconds(100));
        Page.notificationTitleError.shouldBe(visible).shouldHave(text("Ошибка"));
        Page.notificationContentError.shouldBe(visible).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    public void shouldSendEmptyForm() {
        Page.buttonNext.click();
        Page.invalidCardNumberFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
        Page.invalidMonthFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
        Page.invalidYearFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
        Page.emptyCardHolderField.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
        Page.invalidCvCFormat.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    @Test
    public void shouldCloseNotificationWithContentError() {
        Page.fieldCardNumber.setValue(DataHelper.getNotTestCard().getNumberCard());
        Page.fieldMonth.setValue(DataHelper.getNotTestCard().getMonthCard());
        Page.fieldYear.setValue(DataHelper.getNotTestCard().getYearCard());
        Page.fieldCardHolder.setValue(DataHelper.getNotTestCard().getCardHolder());
        Page.fieldCvC.setValue(DataHelper.getNotTestCard().getCvcCard());
        Page.buttonNext.click();
        Page.notificationTitleError.should(appear, Duration.ofSeconds(100));
        Page.notificationTitleError.shouldBe(visible).shouldHave(text("Ошибка"));
        Page.notificationContentError.shouldBe(visible).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
        Page.CloseNotificationWithContentError.click();
        Page.notificationTitleSuccess.shouldBe(visible).shouldHave(text("Успешно"));
        Page.notificationContentSuccess.shouldBe(visible).shouldHave(text("Операция одобрена Банком."));
    }
}