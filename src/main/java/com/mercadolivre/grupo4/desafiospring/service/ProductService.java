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

    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDTO> orderByName(Integer order) {
        return ProductDTO.convert(productRepository.orderByName(order));
    }

    public List<ProductDTO> orderByPrice(Integer order) {
        return ProductDTO.convert(productRepository.orderByPrice(order));
    }

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
        List<Product> resultList = productRepository.get();

        if (name.isPresent()) {
            resultList = productRepository.orderByPrice(name.get());
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
            resultList = productRepository.orderByPrice(price.get());
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
        } else {
            return null;
        }

    }
}
