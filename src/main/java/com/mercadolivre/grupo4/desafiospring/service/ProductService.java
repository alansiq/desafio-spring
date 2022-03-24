package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAllProducts() {
        return productRepository.get();
    }

    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public boolean save(List<Product> productList) {
        // TODO: 24/03/22 Iterar sobre productList e validar todos os campos
        productList.forEach(product -> {
            ProductValidationService.isValid(product);
        });

        return productRepository.addList(productList);
    }
}
