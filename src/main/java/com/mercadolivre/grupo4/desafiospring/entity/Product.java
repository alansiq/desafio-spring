package com.mercadolivre.grupo4.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Product {
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

    @NotNull
    private Boolean freeShipping;

    @NotNull @Pattern(regexp = "[*]{1,5}")
    private String prestige;
}
