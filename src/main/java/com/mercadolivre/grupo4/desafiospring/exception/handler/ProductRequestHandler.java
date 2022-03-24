package com.mercadolivre.grupo4.desafiospring.exception.handler;

import com.mercadolivre.grupo4.desafiospring.exception.ApiException;
import com.mercadolivre.grupo4.desafiospring.exception.ProductRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ProductRequestHandler {

    @ExceptionHandler(value = {ProductRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ProductRequestException e) {

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
