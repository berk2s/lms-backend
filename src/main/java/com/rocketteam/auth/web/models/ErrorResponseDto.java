package com.rocketteam.auth.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {

    /** Status of Response */
    @JsonIgnore
    private HttpStatus httpStatus;

    /** Error type of Response */
    @JsonProperty("error")
    private String error;

    /** Fully error description of Response */
    @JsonProperty("error_description")
    private String errorDescription;

    /** Error code for clients */
    @JsonProperty("code")
    private Integer code;

    public ErrorResponseDto(ErrorType errorType, ErrorDesc errorDescription, HttpStatus httpStatus){
        this.error = errorType.getError();
        this.errorDescription = errorDescription.getDesc();
        this.code = errorDescription.getCode();
        this.httpStatus = httpStatus;
    }

    public ErrorResponseDto(ErrorType errorType, String errorDescription, HttpStatus httpStatus){
        this.error = errorType.getError();
        this.errorDescription = errorDescription;
        this.code = ErrorDesc.getCodeFormDesc(errorDescription);
        this.httpStatus = httpStatus;
    }

    public ErrorResponseDto(ErrorType errorType, HttpStatus httpStatus) {
        this.error = errorType.getError();
        this.errorDescription = httpStatus.getReasonPhrase();
        this.httpStatus = httpStatus;
    }

    public ErrorResponseDto(ErrorType errorType) {
        this.error = errorType.getError();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDescription = HttpStatus.BAD_REQUEST.getReasonPhrase();
    }

}
