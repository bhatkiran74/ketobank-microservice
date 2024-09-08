package com.keto.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans",fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping("api/loans/fetch")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("keto-correlation-id") String correlationId,  @RequestParam String mobileNumber);
}
