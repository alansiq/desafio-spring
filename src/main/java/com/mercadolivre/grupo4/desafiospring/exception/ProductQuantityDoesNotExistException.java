package com.mercadolivre.grupo4.desafiospring.exception;

public class ProductQuantityDoesNotExistException extends RuntimeException{
    public ProductQuantityDoesNotExistException(String message) {
        super(message);
    }
    public ProductQuantityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
