package com.mercadolivre.grupo4.desafiospring.controller;

<<<<<<< Updated upstream
import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

<<<<<<< Updated upstream
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<List<ProductDTO>> insertProduct(@RequestBody List<Product> listProduct){

//        List<ProductDTO> products = productService.

        return null;
=======
    @GetMapping("/products")
    public String listAllProducts() {
        return "Lista de Produtos";

>>>>>>> Stashed changes
    }

}
