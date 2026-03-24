package com.banking.cards.mapper;

import com.banking.cards.dto.CardDto;
import com.banking.cards.entity.Cards;

public class CardsMapper {

    public static Cards toEntity(Cards card, CardDto cardDto) {
       card.setMobileNumber(cardDto.mobileNumber());
            return card;
    }

    public static CardDto toDto(Cards card) {
        return new CardDto(
                card.getCardType(),
                card.getMobileNumber(),
                card.getCardNumber(),
                card.getTotalLimit(),
                card.getAmountUsed(),
                card.getAvailableAmount()
        );
    }
}
