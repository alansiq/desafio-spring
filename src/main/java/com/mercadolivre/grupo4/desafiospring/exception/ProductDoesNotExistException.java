package com.mercadolivre.grupo4.desafiospring.exception;

public class ProductDoesNotExistException extends RuntimeException{
    public ProductDoesNotExistException(String message) {
        super(message);
    }
    public ProductDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
