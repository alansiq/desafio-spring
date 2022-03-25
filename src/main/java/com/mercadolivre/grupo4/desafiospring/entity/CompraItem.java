package com.mercadolivre.grupo4.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompraItem {
    Long productId;
    String name;
    String brand;
    Integer quantity;

    @Override
    public String toString() {
        return "CompraItem{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
