package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDTO> orderByName(int order) {
        return productRepository.orderByName(order);
    }

    public List<ProductDTO> orderByPrice(int order) {
        return productRepository.orderByPrice(order);
    }

    public boolean save(List<Product> productList) {
        return productRepository.addList(productList);
    }

    public List<ProductDTO> productsFilteredBy(Optional<String> name,
                                                 Optional<String> category,
                                                 Optional<String> brand,
                                                 Optional<BigDecimal> price,
                                                 Optional<Boolean> freeShipping,
                                                 Optional<String> prestige)
    {
        List<Product> resultList = productRepository.get();

        if (name.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getName() == null) return false;
                        return product.getName().equals(name.get());
                    }).collect(Collectors.toList());
        }

        if (category.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getCategory() == null) return false;
                        return product.getCategory().equals(category.get());
                    }).collect(Collectors.toList());
        }

        if (brand.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getBrand() == null) return false;
                        return product.getBrand().equals(brand.get());
                    }).collect(Collectors.toList());
        }

        if (price.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getPrice() == null) return false;
                        return product.getPrice().equals(price.get());
                    }).collect(Collectors.toList());
        }

        if (freeShipping.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getFreeShipping() == null) return false;
                        return product.getFreeShipping().equals(freeShipping.get());
                    }).collect(Collectors.toList());
        }

        if (prestige.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getPrestige() == null) return false;
                        return product.getPrestige().equals(prestige.get());
                    }).collect(Collectors.toList());
        }

        return ProductDTO.convert(resultList);
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
