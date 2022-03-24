package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return productRepository.addList(productList);

    }

    public List<Product> returnProductsInStock(List<CompraItem> itemsList){
        List<Product> productsInStock = itemsList.stream().map(
                item -> productRepository.findById(item.getProductId())
        ).collect(Collectors.toList());

        if(!productsInStock.isEmpty()){
            return productsInStock;
        }else{
            return null;
        }

    }
}
