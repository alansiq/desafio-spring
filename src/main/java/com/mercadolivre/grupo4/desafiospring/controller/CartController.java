package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.ProductCartDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Cart;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.CartRepository;
import com.mercadolivre.grupo4.desafiospring.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/api/v1/cart")
    public String checkActiveCarts() {
        return "Endpoint funcionando";
    }

    @PostMapping("api/v1/cart")
    public ResponseEntity<Cart> addProductToCart(@RequestBody ProductCartDTO productCartDTO) {
        Cart currentCart = cartService.addProductToCart(productCartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(currentCart);
    }
}
