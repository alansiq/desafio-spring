package com.mercadolivre.grupo4.desafiospring.repository;

import com.mercadolivre.grupo4.desafiospring.entity.Product;

import java.util.List;

public interface IProductRepository {

    List<Product> getAll();
    boolean addList (List<Product> productList);
    boolean add(Product product);

    Product findById(Long id);


}
