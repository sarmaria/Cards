package com.banking.cards.common;

import com.banking.cards.dto.CardDto;

public class TestData {

    public static final CardDto validCreateRequest = new CardDto("CREDIT", "1234561234", "111111111111", 100000, 50000, 50000);
    public static final CardDto invalidCreateRequest = new CardDto("CREDIT", "123456123", "111111111111", 100000, 50000, 50000);
}
