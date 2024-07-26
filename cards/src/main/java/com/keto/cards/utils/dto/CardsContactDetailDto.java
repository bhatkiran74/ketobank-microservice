package com.keto.cards.utils.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "card")
@Getter
@Setter
public class CardsContactDetailDto {
    private String message;
    private String name;
    private String company;
    private List<String> contact;
    private String city;
}
