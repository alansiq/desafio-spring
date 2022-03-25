package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
  
    @GetMapping("/api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        List<ProductDTO> convertedList = ProductDTO.convert(productService.listAllProducts());
        return ResponseEntity.status(HttpStatus.OK).body(convertedList);
    }

    @PostMapping("/api/v1/product")
    public ResponseEntity<List<ProductDTO>> insertProduct(@Valid @RequestBody List<Product> productList){
        boolean success = productService.save(productList);

        if (success) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(ProductDTO.convert(productList));
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping(path = "/products/{categoryName}")
    public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String categoryName){
        List<ProductDTO> result = productService.findByCategory(categoryName);
        return ResponseEntity.ok(result);
    }

}
