package com.mjc.school.controller.handler;

import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Error> noSuchEntityException(NoSuchEntityException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> validationException(ValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(getErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> argumentException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(getErrorMessage(ServiceErrorCode.ARGUMENT_TYPE_MISMATCH.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> invalidUrl(NoHandlerFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getErrorMessage(ServiceErrorCode.INVALID_URL.getMessage()));
    }

    private Error getErrorMessage(String error) {
        String[] params = error.split("ERROR_MESSAGE: ");
        String errorCode = params[0].replaceFirst("ERROR_CODE: ", "").trim();
        String errorMessage = params[1];


        return new Error(errorCode, errorMessage);
    }
}
