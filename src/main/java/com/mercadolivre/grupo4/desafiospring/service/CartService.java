package com.mercadolivre.grupo4.desafiospring.service;

import com.mercadolivre.grupo4.desafiospring.dto.ProductCartDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Cart;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public Cart addProductToCart(ProductCartDTO productCartDTO) {

        Product product = productService.findByProductId(productCartDTO.getProductId());

        return cartRepository.add(product);
    }


}
