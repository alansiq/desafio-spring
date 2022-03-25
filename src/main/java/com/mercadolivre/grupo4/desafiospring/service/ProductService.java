package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public boolean save(List<Product> productList) {
        return productRepository.addList(productList);
    }

    public List<ProductDTO> productsFilterBy(Optional<String> name, Optional<String> category,
                                             Optional<String> brand, Optional<BigDecimal> price,
                                             Optional<Boolean> freeShipping, Optional<String> prestige,
                                             Optional<Integer> order)
    {
        List<Product> listAfterFilters = productRepository.getAll();

        if (name.isPresent()) listAfterFilters = productRepository.filterByName(name.get());

        if (category.isPresent()) listAfterFilters = productRepository.filterByCategory(category.get());

        if (brand.isPresent()) listAfterFilters = productRepository.filterByBrand(brand.get());

        if (price.isPresent()) listAfterFilters = productRepository.filterByPrice(price.get());

        if (freeShipping.isPresent()) listAfterFilters = productRepository.filterByShipping(freeShipping.get());

        if (prestige.isPresent()) listAfterFilters =productRepository.filterByPrestige(prestige.get());

        return productsOrderBy(order, listAfterFilters);
    }

    public List<ProductDTO> productsOrderBy(Optional<Integer> order, List<Product> listAfterFilters) {
        List<Product> listAfterOrder = listAfterFilters;

        if (order.isPresent()) listAfterOrder = productRepository.orderByName(order.get());

        return ProductDTO.convert(listAfterOrder);
    }

    public List<Product> returnProductsInStock(List<CompraItem> itemsList){
        List<Product> productsInStock = itemsList.stream().map(
                item -> productRepository.findById(item.getProductId())
        ).collect(Collectors.toList());

        if(!productsInStock.isEmpty()){
            return productsInStock;
        } else {
            return null;
        }

    }
}
