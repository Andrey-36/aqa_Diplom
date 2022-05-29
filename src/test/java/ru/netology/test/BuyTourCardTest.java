package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.DatabaseQuery;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class BuyTourCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        DatabaseQuery.deleteTables();
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:8080/");
    }

    @Test
    void shouldBuyTourWithApprovedCard() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = DatabaseQuery.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldBuyTourWithDeclinedCard() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getDeclinedCard(), getMonthCard(0), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = DatabaseQuery.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldBuyTourWithApprovedCardExpires() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(0), getYearCard(0), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = DatabaseQuery.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldBuyTourWithDeclinedCardExpires() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getDeclinedCard(), getMonthCard(0), getYearCard(0), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = DatabaseQuery.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldBuyTourWithCardEmptyFieldCardNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                null, getMonthCard(1), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumber(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardNumberShort() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidShortCardNumber(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWithCardEmptyFieldMonth() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), null, getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWrongCardExpiryMonth() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(-1), getYearCard(0), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldBuyTourWithCardInvalidMonthOneNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardOneNumber(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWithInvalidCardInvalidPeriodMonths() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardInvalidPeriod(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldBuyTourCardInvalidMonth00() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCard(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldBuyTourWithCardEmptyFieldYears() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), null, getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWrongCardExpiryYear() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(0), getYearCard(-1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkCardExpired();
    }

    @Test
    void shouldBuyTourWithCardValidMoreThanFiveYears() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(6), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardYearOneNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getInvalidYearCard(), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWithCardEmptyFieldCardHolder() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(3), null, getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldBuyTourWithCardIncorrectlyCardholder() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardHolder();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardholderCyrillic() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3),
                getInvalidCardHolderCyrillic(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardholderWithNumbers() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolderWithNumbers(),
                getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldBuyTourWithCardInvalidCardholderNameConsistingOneLetter() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolderOneLetterName(),
                getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldBuyTourWithCardEmptyFieldCvc() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(3), getCardHolder(), null);
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldBuyTourWithCardInvalidCvcShort() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(2), getCardHolder(), getInvalidCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldBuyTourWithCardEmptyAllField() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                null, null, null, null, null);
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkAllFieldsMustBeFilled();
    }
}