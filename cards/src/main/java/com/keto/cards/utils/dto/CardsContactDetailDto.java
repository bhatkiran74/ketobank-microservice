package com.keto.cards.utils.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "card")
public record CardsContactDetailDto(
        String message,
        String name,
        String company,
        List<String> contact,
        String city
) {

}
