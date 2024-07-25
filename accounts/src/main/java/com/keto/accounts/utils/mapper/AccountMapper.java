package com.keto.accounts.utils.mapper;

import com.keto.accounts.entity.Account;
import com.keto.accounts.utils.dto.AccountsDto;

public class AccountMapper {
    /**
     * Maps an AccountsDto to an Account entity.
     *
     * @param dto The AccountsDto.
     * @param account The Account entity to populate.
     * @return The populated Account entity.
     */
    public static Account mapToAccounts(AccountsDto dto, Account account) {
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBranchAddress(dto.getBranchAddress());
        return account;
    }
    /**
     * Maps an Account entity to an AccountsDto.
     *
     * @param account The Account entity.
     * @param accountsDto The AccountsDto to populate.
     * @return The populated AccountsDto.
     */
    public static AccountsDto mapToAccountsDto(Account account, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());
        return accountsDto;
    }
}
