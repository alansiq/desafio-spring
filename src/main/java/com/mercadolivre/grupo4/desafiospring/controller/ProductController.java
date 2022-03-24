package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
  
//    @GetMapping("/api/v1/products")
//    public ResponseEntity<List<ProductDTO>> listAllProducts() {
//        List<ProductDTO> convertedList = ProductDTO.convert(productService.listAllProducts());
//        return ResponseEntity.status(HttpStatus.OK).body(convertedList);
//    }

    @GetMapping("api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAllProductsFiltered(@RequestParam(required = false) Optional<String> name ,
                                                                    @RequestParam(required = false) Optional<String> category,
                                                                    @RequestParam(required = false) Optional<String> brand,
                                                                    @RequestParam(required = false) Optional<BigDecimal> price,
                                                                    @RequestParam(required = false) Optional<Boolean> freeShipping,
                                                                    @RequestParam(required = false) Optional<String> prestige
                                                                    )
    {

        List<ProductDTO> result = productService.listFilteredProducts(name, category, brand, price, freeShipping, prestige);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/v1/product")
    public ResponseEntity<List<ProductDTO>> insertProduct(@RequestBody List<Product> productList){
        boolean success = productService.save(productList);
        if (success) {
            return  ResponseEntity.ok().body(ProductDTO.convert(productList));
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping(path = "/products/{categoryName}")
    public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String categoryName){
        List<ProductDTO> result = productService.findByCategory(categoryName);
        return ResponseEntity.ok(result);
    }

}
