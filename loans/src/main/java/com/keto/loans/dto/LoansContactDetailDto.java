package com.keto.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "loan")
@Getter
@Setter
public class LoansContactDetailDto{
    private String message;
    private String name;
    private String company;
    private List<String> contact;
    private String city;
}
