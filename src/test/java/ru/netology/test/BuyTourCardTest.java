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
    void shouldGetErrorBuyTourWithDeclinedCard() {
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
    void shouldGetErrorBuyTourWithDeclinedCardExpires() {
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
    void shouldGetErrorBuyTourWithCardEmptyFieldCardNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                null, getMonthCard(1), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumber(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumberShort() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidShortCardNumber(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumberLong() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidLongCardNumber(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumberZero() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumberZero(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumberLetter() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumberLetter(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardNumberSymbols() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumberSymbols(), getMonthCard(2), getYearCard(1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardEmptyFieldMonth() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), null, getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWrongCardExpiryMonth() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(-1), getYearCard(0), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidMonthOneNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardOneNumber(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithInvalidCardInvalidPeriodMonths() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardInvalidPeriod(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidMonthZero() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCard(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidMonthLetter() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardLetter(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidMonthSymbols() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getInvalidMonthCardSymbols(), getYearCard(2), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardEmptyFieldYears() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), null, getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWrongCardExpiryYear() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(0), getYearCard(-1), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkCardExpired();
    }

    @Test
    void shouldGetErrorBuyTourWithCardValidMoreThanFiveYears() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(6), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardYearOneNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getInvalidYearCard(), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardYearLongNumber() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getInvalidYearCardLong(), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardYearZero() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getInvalidYearCardZero(), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkCardExpired();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardYearSymbols() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getInvalidYearCardSymbols(), getCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardEmptyFieldCardHolder() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(3), null, getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldGetErrorBuyTourWithCardIncorrectlyCardholder() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolder(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidCardHolder();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardholderCyrillic() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3),
                getInvalidCardHolderCyrillic(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardholderWithNumbers() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolderWithNumbers(),
                getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardholderWithSymbols() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolderSymbols(),
                getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCardholderNameConsistingOneLetter() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(3), getInvalidCardHolderOneLetterName(),
                getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.wrongCardHolder();
    }

    @Test
    void shouldGetErrorBuyTourWithCardEmptyFieldCvc() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(3), getCardHolder(), null);
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidCvcZero() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(2), getCardHolder(), getInvalidCvcZero());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidCvcLetter() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(2), getCardHolder(), getInvalidCvcLetter());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourCardInvalidCvcSymbols() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(2), getYearCard(2), getCardHolder(), getInvalidCvcSymbols());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardInvalidCvcShort() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                getApprovedCard(), getMonthCard(1), getYearCard(2), getCardHolder(), getInvalidCvc());
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldGetErrorBuyTourWithCardEmptyAllField() {
        var startPage = new StartPage();
        CardInfo card = new CardInfo(
                null, null, null, null, null);
        var paymentPage = startPage.payment();
        paymentPage.getCardDataEntry(card);
        paymentPage.checkAllFieldsMustBeFilled();
    }
}