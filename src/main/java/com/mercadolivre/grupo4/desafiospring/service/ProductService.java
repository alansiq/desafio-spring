package com.mercadolivre.grupo4.desafiospring.service;

import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;













































    public List<Product> listAllProducts() {
        return productRepository.readAllProducts();
    }


    public List<Product> listAlphabeticalOrder() {
        List<Product> rawList = productRepository.readAllProducts();
        return rawList.sort(Comparator.comparing(Product::getName));

    }
}
