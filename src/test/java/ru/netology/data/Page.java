package ru.netology.data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Page {
    public static SelenideElement buttonBuy = $(withText("Купить"));
    public static SelenideElement buttonBuyCredit = $(withText("Купить в кредит"));
    public static SelenideElement fieldCardNumber = $("[placeholder='0000 0000 0000 0000']");
    public static SelenideElement invalidCardNumberFormat = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[3]");
    public static SelenideElement fieldMonth = $("[placeholder='08']");
    public static SelenideElement invalidMonthFormat = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[3]");
    public static SelenideElement fieldYear = $("[placeholder='22']");
    public static SelenideElement invalidYearFormat = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[3]");
    public static SelenideElement fieldCardHolder = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[2]/input");
    public static SelenideElement emptyCardHolderField = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[3]");
    public static SelenideElement fieldCvC = $("[placeholder='999']");
    public static SelenideElement invalidCvCFormat = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[3]");
    public static SelenideElement buttonNext = $(withText("Продолжить"));
    public static SelenideElement notificationTitleSuccess = $(".notification_status_ok .notification__title");
    public static SelenideElement notificationContentSuccess = $(".notification_status_ok .notification__content");
    public static SelenideElement notificationTitleError = $(".notification_status_error .notification__title");
    public static SelenideElement notificationContentError =  $(".notification_status_error .notification__content");
    public static SelenideElement CloseNotificationWithContentError = $x("//*[@id=\"root\"]/div/div[3]/button/span/span/span");
}
