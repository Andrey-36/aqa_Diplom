package ru.netology.data;

import lombok.Value;

public class DataHelper {

    @Value
    public static class Card {
        String numberCard;
        String monthCard;
        String yearCard;
        String cardHolder;
        String cvcCard;
    }

    public static Card getApprovedCard() {
        return new Card(
                "4444444444444441",
                "07",
                "22",
                "ANDREY K",
                "897"
        );
    }

    public static Card getDeclinedCard() {
        return new Card(
                "4444444444444442",
                "07",
                "22",
                "ANDREY K",
                "897"
        );
    }

    public static Card getNotTestCard() {
        return new Card(
                "4444444444444443",
                "07",
                "22",
                "ANDREY K",
                "897"
        );
    }
}