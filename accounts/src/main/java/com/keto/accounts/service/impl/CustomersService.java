package com.keto.accounts.service.impl;

import com.keto.accounts.entity.Account;
import com.keto.accounts.entity.Customer;
import com.keto.accounts.exception.ResourseNotFoundException;
import com.keto.accounts.repository.AccountRepository;
import com.keto.accounts.repository.CustomerRepository;
import com.keto.accounts.service.ICustomersService;
import com.keto.accounts.service.client.CardsDto;
import com.keto.accounts.service.client.CardsFeignClient;
import com.keto.accounts.service.client.LoansDto;
import com.keto.accounts.service.client.LoansFeignClient;
import com.keto.accounts.utils.dto.AccountsDto;
import com.keto.accounts.utils.dto.CustomerDetailsDto;
import com.keto.accounts.utils.mapper.AccountMapper;
import com.keto.accounts.utils.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CustomersService implements ICustomersService {


    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardsFeignClient cardsFeignClient;
    @Autowired
    LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourseNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourseNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.findCardsDetailsByMobileNumber(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
