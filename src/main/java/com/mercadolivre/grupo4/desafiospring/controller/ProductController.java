package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<List<ProductDTO>> insertProduct(@RequestBody List<Product> listProduct) {


//        List<ProductDTO> products = productService.

        return null;


    }































    @GetMapping("/api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        List<ProductDTO> convertedList = ProductDTO.convert(productService.listAllProducts());
        return ResponseEntity.status(HttpStatus.OK).body(convertedList);

    }

    @GetMapping("/api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAlphabeticalOrder(@RequestParam String order) {
        List<ProductDTO> convertedList = ProductDTO.convert(productService.listAlphabeticalOrder())
    }
}
