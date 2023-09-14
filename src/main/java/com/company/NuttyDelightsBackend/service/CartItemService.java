package com.company.NuttyDelightsBackend.service;

import com.company.NuttyDelightsBackend.exception.CartItemException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.CartItem;
import com.company.NuttyDelightsBackend.model.Product;
import com.company.NuttyDelightsBackend.model.Variants;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(String userId, String id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, Variants variant, String userId);

    public void removeCartItem(String userId,String cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(String cartItemId) throws CartItemException;
}
