package com.keto.accounts.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Customer & Account details ")
public class AccountsDto {
    @Schema(description = "Account Number",example = "2589631470")
    @NotEmpty(message = "The accountNumber field cannot be left empty. Please enter accountNumber to proceed.")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "The accountNumber number must contain exactly 10 digits. Please enter a valid accountNumber to proceed.")
    private Long accountNumber;

    @Schema(description = "Account Type",example = "Saving/Current")
    @NotEmpty(message = "The accountType field cannot be left empty. Please enter accountType to proceed.")
    private String accountType;

    @Schema(description = "Branch Address",example = "Ausa Road, Latur-413512")
    @NotEmpty(message = "The branchAddress field cannot be left empty. Please enter branchAddress to proceed.")
    private String branchAddress;
}
