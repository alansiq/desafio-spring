package com.mercadolivre.grupo4.desafiospring.repository;

import com.mercadolivre.grupo4.desafiospring.entity.Product;

import java.util.List;

public interface IProductRepository {

    List<Product> get();
    boolean addList (List<Product> productList);
    boolean add(Product product);


}
