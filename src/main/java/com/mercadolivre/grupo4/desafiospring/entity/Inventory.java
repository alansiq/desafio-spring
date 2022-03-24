package com.mercadolivre.grupo4.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    private String productName;
    private Integer qtyExisting;
    private BigDecimal priceOfProduct;
    List<Product> stock = new ArrayList<>();

    public String existsProduct(String productName, List<Product> stock){
        String searchProduct = String.valueOf(stock.stream().filter(product -> product.getName().equals(productName)));
        if(searchProduct == null || searchProduct == ""){
            return "Product not exist.";
        }
        return "Purchase allowed.";
    }

    public String verifyQuantity(Integer qtyExisting, Integer qtyPurchased){
        if(qtyPurchased > qtyExisting) {
            return "Request not allowed. Quantity avaliable: " + qtyExisting;
        }
        return "Purchase allowed.";
    }
}
