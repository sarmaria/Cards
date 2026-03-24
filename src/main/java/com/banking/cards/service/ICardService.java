package com.banking.cards.service;

import com.banking.cards.dto.CardDto;

public interface ICardService {
    void createCard(String mobileNumer);

    /**
     * Fetch the details of the card related to the mobile number
     * @param mobileNumber
     * @return CardDto will all card details
     */
    CardDto fetchCardDetails(String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(String mobileNumber);
}
