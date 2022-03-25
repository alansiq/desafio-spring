package com.mercadolivre.grupo4.desafiospring.repository;


import com.mercadolivre.grupo4.desafiospring.entity.Cart;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.TreeMap;

@Repository
public class CartRepository {
    private final Cart activeCart = new Cart();

    public Cart add(Product product) {
        int size = activeCart.getProductList().size();
        if (size == 0) {
            activeCart.getProductList().put(0L, product);
            activeCart.calculatePrice();
            return this.activeCart;
        }

        Long lastId = activeCart.getProductList().lastKey();
        activeCart.getProductList().put(lastId + 1, product);
        activeCart.calculatePrice();
        return this.activeCart;
    }



}
