package com.keto.accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> findCardsDetailsByMobileNumber(String correlationId, String mobileNumber) {
        return null;
    }
}
