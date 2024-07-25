package com.keto.accounts.utils.mapper;

import com.keto.accounts.entity.Customer;
import com.keto.accounts.utils.dto.CustomerDto;

public class CustomerMapper {

    /**
     * Maps a CustomerDto to a Customer entity.
     *
     * @param customerDto The CustomerDto.
     * @param customer The Customer entity to populate.
     * @return The populated Customer entity.
     */
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    /**
     * Maps a Customer entity to a CustomerDto.
     *
     * @param customer The Customer entity.
     * @param customerDto The CustomerDto to populate.
     * @return The populated CustomerDto.
     */
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }
}
