package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<ShoppingCart> getAllShoppingCarts();
    Optional<ShoppingCart> getShoppingCartById(Long id);
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
    void deleteShoppingCartById(Long id);
}
