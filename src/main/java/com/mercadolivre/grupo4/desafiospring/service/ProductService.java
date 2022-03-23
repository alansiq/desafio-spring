package com.mercadolivre.grupo4.desafiospring.service;

import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;



}
