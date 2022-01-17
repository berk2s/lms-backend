package com.rocketteam.auth.web.exceptions;

import com.rocketteam.auth.web.models.ErrorResponseDto;
import com.rocketteam.auth.web.models.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

//    @ExceptionHandler(NullPointerException.class)
//    protected ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException ex) {
//        log.warn("NullPointerException: {} {}", ex.getLocalizedMessage(), ex.getMessage());
//        return errorResponse(new ErrorResponseDto(ErrorType.SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_GRANT, ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserAlreadyTakenException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyTakenException(UserAlreadyTakenException ex) {
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<ErrorResponseDto> handleJWTException(JWTException ex) {
        return errorResponse(new ErrorResponseDto(ErrorType.SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(InvalidGrantException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidGrantException(InvalidGrantException ex) {
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    private ResponseEntity<ErrorResponseDto> errorResponse(ErrorResponseDto errorResponseDto){
        return new ResponseEntity<>(errorResponseDto, errorResponseDto.getHttpStatus());
    }
}
