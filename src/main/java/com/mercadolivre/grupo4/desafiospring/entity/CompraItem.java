package com.mercadolivre.grupo4.desafiospring.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraItem {
    private Long productId;
    private String name;
    private String brand;
    private Integer quantity;

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
