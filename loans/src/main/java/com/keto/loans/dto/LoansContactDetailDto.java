package com.keto.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "loan")
public record LoansContactDetailDto(
        String message,
        String name,
        String company,
        List<String> contact,
        String city
) {

}
