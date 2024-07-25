package com.keto.cards.controller;

import com.keto.cards.exception.ResourceNotFoundException;
import com.keto.cards.services.ICardsService;
import com.keto.cards.utils.constants.CardsConstants;
import com.keto.cards.utils.dto.CardsContactDetailDto;
import com.keto.cards.utils.dto.CardsDto;
import com.keto.cards.utils.dto.ErrorResponseDto;
import com.keto.cards.utils.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
/**
 * CardsController.java
 * Author: Kiransing bhat
 * Description: This class implements CardsController
 **/
@Tag(name = "Cards Management", description = "Endpoints for managing cards")
@RestController
@RequestMapping(path = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    @Autowired
    private ICardsService iCardsService;

    /**
     * Endpoint to create a new card based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer for whom the card is being created.
     * @return ResponseEntity with status 201 (Created) and a response body containing status and message.
     */
    @Operation(summary = "Create a new card", description = "Creates a new card based on the provided mobile number")
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
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")  String mobileNumber){
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }

    /**
     * Endpoint to find card details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer's card.
     * @return ResponseEntity with status 200 (OK) and a response body containing the card details.
     */
    @Operation(summary = "Fetch card details", description = "Fetches card details based on the provided mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details fetched successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardsDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Card details not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> findCardsDetailsByMobileNumber(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        CardsDto cardsDto = iCardsService.findCardsDetailsByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    /**
     * Updates the card details based on the provided CardsDto.
     *
     * @param cardsDto The DTO containing updated card information.
     * @return ResponseEntity containing the status and message of the update operation.
     * @throws ResourceNotFoundException if no card is found with the given card number.
     */
    @Operation(summary = "Update card details", description = "Updates card details based on the provided CardsDto object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "417", description = "Failed to update card details",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) })
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardsDto cardsDto){
        boolean isUpdated = iCardsService.updateCard(cardsDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }
    /**
     * Deletes card details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer's card.
     * @return ResponseEntity containing the status and message of the delete operation.
     * @throws ResourceNotFoundException if no card is found with the given mobile number.
     */
    @Operation(summary = "Delete card details", description = "Deletes card details based on the provided mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "417", description = "Failed to delete card details",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) })
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

    @Autowired
    private CardsContactDetailDto cardsContactDetailDto;
    @GetMapping("/contact-details")
    public ResponseEntity<CardsContactDetailDto> getContactDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactDetailDto);
    }


}
