package com.mercadolivre.grupo4.desafiospring.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class ProductCartDTO {
    @NotNull
    private Long productId;

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    private String brand;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer quantity;
}
