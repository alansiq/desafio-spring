package com.mercadolivre.grupo4.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
    private BigDecimal totalPrice;
    private final TreeMap<Long, Product> productList = new TreeMap<>();

    public void calculatePrice() {
        BigDecimal totalPrice = BigDecimal.valueOf(0L);

        if(this.productList.size() > 0) {
            for (Product product : productList.values()) {
              totalPrice = totalPrice.add(product.getPrice());
            }
        }

        this.totalPrice = totalPrice;
    }
}
