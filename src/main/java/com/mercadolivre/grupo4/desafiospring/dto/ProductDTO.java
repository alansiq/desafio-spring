package com.mercadolivre.grupo4.desafiospring.dto;

import com.mercadolivre.grupo4.desafiospring.entity.Product;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductDTO implements Serializable {

    private long productId;
    private String name;
    private int quantity;

    public ProductDTO (Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.quantity = product.getQuantity();

    }

    public static List<ProductDTO> convert(List<Product> products){

        return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
