package com.keto.cards.services;

import com.keto.cards.utils.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto findCardsDetailsByMobileNumber(String mobileNumber);
    boolean updateCard(CardsDto cardsDto);
    boolean deleteCard(String mobileNumber);

}
