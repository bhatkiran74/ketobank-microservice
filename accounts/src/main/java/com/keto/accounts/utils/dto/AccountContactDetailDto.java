package com.keto.accounts.utils.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "account")
public record AccountContactDetailDto(
        String message,
        String name,
        String company,
        List<String> contact,
        String city
) {

}
