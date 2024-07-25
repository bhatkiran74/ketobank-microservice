package com.keto.accounts.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(name = "Response", description = "Success message")
@AllArgsConstructor
public class ResponseDto {
    @Schema(name = "Status Code", example = "200")
    private String statusCode;
    @Schema(name = "Status Message", example = "Success")
    private String statusMsg;
}
