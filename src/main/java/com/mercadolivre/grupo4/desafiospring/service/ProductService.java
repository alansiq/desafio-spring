package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<ProductDTO> productsFilteredBy(Optional<Integer> name,
                                                 Optional<String> category,
                                                 Optional<String> brand,
                                                 Optional<Integer> price,
                                                 Optional<Boolean> freeShipping,
                                                 Optional<String> prestige)
    {
        List<Product> resultList = productRepository.getAll();

        if (name.isPresent()) resultList = productRepository.orderByName(name.get());

        if (category.isPresent()) resultList = productRepository.filterByCategory(category.get());

        if (brand.isPresent()) resultList = productRepository.filterByBrand(brand.get());

        if (price.isPresent()) resultList = productRepository.orderByPrice(price.get());

        if (freeShipping.isPresent()) resultList = productRepository.filterByShipping(freeShipping.get());

        if (prestige.isPresent()) resultList =productRepository.filterByPrestige(prestige.get());

        return ProductDTO.convert(resultList);
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
