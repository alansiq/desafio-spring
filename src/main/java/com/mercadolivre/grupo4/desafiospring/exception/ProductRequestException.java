package com.mercadolivre.grupo4.desafiospring.exception;

public class ProductRequestException extends RuntimeException{
    public ProductRequestException(String message) {
        super(message);
    }

    public ProductRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
