package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Comparator;
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

    public List<ProductDTO> orderByName(int order) {
        return productRepository.orderBy(order);
    }

    public boolean save(List<Product> productList) {
        return productRepository.addList(productList);
    }
}
