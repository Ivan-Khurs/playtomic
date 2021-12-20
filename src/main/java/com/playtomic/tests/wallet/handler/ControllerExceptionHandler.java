package com.playtomic.tests.wallet.handler;

import com.playtomic.tests.wallet.api.response.ErrorResponse;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
                       MethodArgumentNotValidException.class})
    public final ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(WalletNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }
}
