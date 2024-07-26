package com.keto.accounts.utils.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "account")
@Getter
@Setter
public class AccountContactDetailDto {
    private String message;
    private String name;
    private String company;
    private List<String> contact;
    private String city;
}
