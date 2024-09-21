package com.keto.accounts.controller;

import com.keto.accounts.service.IAccountsService;
import com.keto.accounts.utils.constants.AccountsConstants;
import com.keto.accounts.utils.dto.AccountContactDetailDto;
import com.keto.accounts.utils.dto.CustomerDto;
import com.keto.accounts.utils.dto.ErrorResponseDto;
import com.keto.accounts.utils.dto.ResponseDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * AccountsController.java
 * Author: Kiransing bhat
 * Description: This class implements AccountsController
 **/
@Tag(name = "Customer Account Management", description = "Endpoints for managing customer accounts")
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;
    /**
     * Endpoint to create a new account based on the provided CustomerDto.
     *
     * @param customerDto The CustomerDto containing customer information.
     * @return ResponseEntity with status 201 (Created) and a response body indicating success.
     */
    @Operation(summary = "Create a new customer account", description = "Creates a new customer account based on the provided CustomerDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED) //header
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));//body
    }
    /**
     * Endpoint to fetch account details for a customer by mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return ResponseEntity with status 200 (OK) and the CustomerDto containing customer and account details.
     */
    @Operation(summary = "Fetch customer account details", description = "Fetches customer account details based on the provided mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> findAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "The Mobile number must contain exactly 10 digits. Please enter a valid Mobile Number to proceed.") String mobileNumber){
        CustomerDto customerDto= iAccountsService.findAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
    /**
     * Endpoint to update account details based on the provided CustomerDto.
     *
     * @param customerDto The CustomerDto containing updated account and customer information.
     * @return ResponseEntity with status 200 (OK) and a success response if update is successful,
     *         or status 500 (Internal Server Error) and an error response if update fails.
     */
    @Operation(summary = "Update customer account details", description = "Updates customer account details based on the provided CustomerDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }
    /**
     * Endpoint to delete an account by mobile number.
     *
     * @param mobileNumber The mobile number of the customer whose account is to be deleted.
     * @return ResponseEntity with status 200 (OK) and a success response if deletion is successful,
     *         or status 417 (Expectation Failed) and an error response if deletion fails.
     */
    @Operation(summary = "Delete a customer account", description = "Deletes a customer account based on the provided mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam     @Pattern(regexp = "(^$|[0-9]{10})",message = "The Mobile number must contain exactly 10 digits. Please enter a valid Mobile Number to proceed.")
                                                         String mobileNumber){
       boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Value("${build.version}")
    private String buildVersion;

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Retry(name ="getBuildVersion",fallbackMethod ="getBuildVersionFallback" )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        logger.debug("getBuildVersion : ");
        throw new NullPointerException();
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildVersionFallback(Throwable throwable){
        logger.debug("getBuildVersionFallback : ");
        return ResponseEntity.status(HttpStatus.OK).body("0.0.0.0");
    }

    @Autowired
    private Environment environment;
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Autowired
    private AccountContactDetailDto accountContactDetailDto;

    @RateLimiter(name = "getContactDetails")
    @GetMapping("/contact-details")
    public ResponseEntity<AccountContactDetailDto> getContactDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(accountContactDetailDto);
    }
}
