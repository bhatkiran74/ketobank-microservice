package com.keto.cards.services.impl;

import com.keto.cards.repository.CardsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class CardsServiceImplTest {

    @InjectMocks
    CardsServiceImpl cardsService;

    @Mock
    private CardsRepository cardsRepository;

    @BeforeEach
    void setup(){
//        cardsRepository.save();
    }

    @Test
    void createCard() {
        String mobileNumber = "8999025679";
        cardsService.createCard(mobileNumber);
    }

    @Test
    void createCardThrowException() {
        String mobileNumber = "8999025679";
        cardsService.createCard(mobileNumber);
    }

    @Test
    void findCardsDetailsByMobileNumber() {
    }

    @Test
    void updateCard() {
    }

    @Test
    void deleteCard() {
    }
}