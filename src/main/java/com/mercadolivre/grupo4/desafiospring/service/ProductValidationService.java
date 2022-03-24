package com.mercadolivre.grupo4.desafiospring.service;

import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.exception.ProductRequestException;

public class ProductValidationService {

    public static boolean isValid(Product product) {

        if (
                product.getName() == null ||
                product.getCategory() == null ||
                product.getFreeShipping() == null ||
                product.getPrestige() == null ||
                product.getProductId() == null ||
                product.getBrand() == null
        ) throw new ProductRequestException("Product parameters can not be null");
        return true;
        // TODO: 24/03/22 é gambiarra, mas funciona
    }
}
