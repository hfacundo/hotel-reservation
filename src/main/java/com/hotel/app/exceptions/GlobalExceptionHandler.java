package com.hotel.app.exceptions;

import com.hotel.app.response.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class validates that mandatory fields in Reservation object are not null or empty
 *
 * Mandatory fields in Reservation object are:
 *
 *      clientFullName
 *      fromDate
 *      toDate
 *
 * If any is missing it will throw 400 exception with its respective error message indicating field that is missing
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BasicResponse<String>> handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMessage
                    .append("Field missing: '").append(error.getField()).append("', ").append(error.getDefaultMessage())
                    .append(System.lineSeparator());
        }

        // Populating a BasicResponse object to return a proper response
        BasicResponse<String> response = new BasicResponse<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
