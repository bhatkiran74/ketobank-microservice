package com.keto.accounts.service.client;


import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards/fetch",consumes = "application/json")
    public ResponseEntity<CardsDto> findCardsDetailsByMobileNumber(@RequestParam String mobileNumber);
}
