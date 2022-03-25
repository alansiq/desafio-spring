package com.mercadolivre.grupo4.desafiospring.dto;


import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompraItemDTO implements Serializable {
     private Long productId;
     private String name;
     private String brand;
     private Integer quantity;

     public CompraItemDTO(CompraItem compraItem) {
          this.productId = compraItem.getProductId();
          this.name = compraItem.getName();
          this.brand = compraItem.getBrand();
          this.quantity = compraItem.getQuantity();
     }

     @Override
     public String toString() {
          return "CompraItemDTO{" +
                  "productId=" + productId +
                  ", name='" + name + '\'' +
                  ", brand='" + brand + '\'' +
                  ", quantity=" + quantity +
                  '}';
     }
}
