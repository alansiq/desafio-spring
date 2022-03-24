package com.mercadolivre.grupo4.desafiospring.dto;


import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompraItemDTO implements Serializable {
     Long productId;
     String name;
     String brand;
     Integer quantity;

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
