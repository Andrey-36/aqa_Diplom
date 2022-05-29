package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement headingPayment = $$("h3.heading").find(exactText("Оплата по карте"));
    private SelenideElement fieldCardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("input[placeholder='08']");
    private SelenideElement fieldYear = $("input[placeholder='22']");
    private SelenideElement fieldCardHolder = $$(".input__control").get(3);
    private SelenideElement fieldCvc = $("input[placeholder='999']");
    private SelenideElement buttonContinue = $$(".button").find(exactText("Продолжить"));
    private final SelenideElement errorMessage = $(byText("Ошибка! Банк отказал в проведении операции."));

    public PaymentPage() {
        headingPayment.shouldBe(visible);
    }

    public void getCardDataEntry(CardInfo cardInfo) {
        fieldCardNumber.setValue(cardInfo.getNumberCard());
        fieldMonth.setValue(cardInfo.getMonth());
        fieldYear.setValue(cardInfo.getYear());
        fieldCardHolder.setValue(cardInfo.getCardHolder());
        fieldCvc.setValue(cardInfo.getCvc());
        buttonContinue.click();
    }

    public void successfulPaymentDebitCard() {
        $(".notification_status_ok")
                .shouldHave(text("Успешно Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void invalidPaymentDebitCard() {
        $(".notification_status_error")
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(20)).shouldBe(visible);
    }

    public void checkInvalidFormat() {
        $(".input__sub").shouldBe(visible).shouldHave(text("Неверный формат"), Duration.ofSeconds(15));
    }

    public void checkInvalidCardValidityPeriod() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Неверно указан срок действия карты"), Duration.ofSeconds(15));
    }

    public void checkCardExpired() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Истёк срок действия карты"), Duration.ofSeconds(15));
    }

    public void checkInvalidCardHolder() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Введите имя и фамилию, указанные на карте"), Duration.ofSeconds(15));
    }

    public void wrongCardHolder() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Значение поля может содержать только латинские буквы и дефис"), Duration.ofSeconds(15));
    }

    public void checkEmptyField() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    public void checkAllFieldsMustBeFilled() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5))
                .shouldHave(CollectionCondition.texts("Поле обязательно для заполнения"));
    }
}