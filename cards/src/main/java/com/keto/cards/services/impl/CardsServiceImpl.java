package com.keto.cards.services.impl;

import com.keto.cards.entity.Cards;
import com.keto.cards.exception.CardAlreadyExistsException;
import com.keto.cards.exception.ResourceNotFoundException;
import com.keto.cards.repository.CardsRepository;
import com.keto.cards.services.ICardsService;
import com.keto.cards.utils.constants.CardsConstants;
import com.keto.cards.utils.dto.CardsDto;
import com.keto.cards.utils.mapper.CardMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    /**
     * Creates a new card for the customer with the given mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @throws CardAlreadyExistsException if a card is already registered with the given mobile number.
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewcard(mobileNumber));
    }

    /**
     * Finds card details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer's card.
     * @return CardsDto containing the card details.
     * @throws ResourceNotFoundException if no card is found with the given mobile number.
     */
    @Override
    public CardsDto findCardsDetailsByMobileNumber(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->
                        new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

        return CardMapper.mapToCardsDto(cards,new CardsDto());
    }

    /**
     * Updates the details of an existing card based on the provided CardsDto.
     *
     * @param cardsDto The DTO containing updated card information.
     * @return true if the update was successful.
     * @throws ResourceNotFoundException if no card is found with the given card number.
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardMapper.mapToCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }
    /**
     * Deletes a card based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer's card.
     * @return true if the deletion was successful.
     * @throws ResourceNotFoundException if no card is found with the given mobile number.
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    /**
     * Helper method to create a new card entity.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @return A new Cards entity.
     */
    private Cards createNewcard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
