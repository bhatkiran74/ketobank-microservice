package com.keto.accounts.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse",description = "ErrorResponse hold error information")
public class ErrorResponseDto {
    @Schema(description = "The API path where the error occurred", example = "/api/v1/resource")
    private String apiPath;

    @Schema(description = "The HTTP status code of the error", example = "404")
    private HttpStatus errorCode;

    @Schema(description = "The error message", example = "Resource not found")
    private String errorMessage;

    @Schema(description = "The time the error occurred", example = "2023-06-27T10:15:30")
    private LocalDateTime errorTime;
}
