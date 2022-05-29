package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedCard() {
        return ("4444444444444441");
    }

    public static String getDeclinedCard() {
        return ("4444444444444442");
    }

    public static String getInvalidCardNumber() {
        return faker.finance().creditCard();
    }

    public static String getInvalidShortCardNumber() {
        return faker.numerify("#### #### #### ###");
    }

    public static String getMonthCard(int plusMonth) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonthCard() {
        return "00";
    }

    public static String getInvalidMonthCardOneNumber() {
        return faker.numerify("#");
    }

    public static String getInvalidMonthCardInvalidPeriod() {
        return "21";
    }

    public static String getYearCard(int plusYears) {
        return LocalDate.now().plusYears(plusYears).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYearCard() {
        return faker.numerify("#");
    }

    public static String getCardHolder() {
        return faker.name().name();
    }

    public static String getInvalidCardHolder() {
        return faker.name().firstName();
    }

    public static String getInvalidCardHolderCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().name();
    }

    public static String getInvalidCardHolderWithNumbers() {
        return (faker.name().firstName() + faker.numerify("#######"));
    }

    public static String getInvalidCardHolderOneLetterName() {
        return "A";
    }

    public static String getCvc() {
        return faker.numerify("###");
    }

    public static String getInvalidCvc() {
        return faker.numerify("##");
    }
}