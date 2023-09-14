package com.company.NuttyDelightsBackend.service;

import com.company.NuttyDelightsBackend.dto.request.AddItemRequest;
import com.company.NuttyDelightsBackend.dto.request.CartItemDTO;
import com.company.NuttyDelightsBackend.dto.response.CartItemResponseDTO;
import com.company.NuttyDelightsBackend.dto.response.CartResponseDTO;
import com.company.NuttyDelightsBackend.dto.response.RemoveResponse;
import com.company.NuttyDelightsBackend.exception.ProductException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.CartItem;
import com.company.NuttyDelightsBackend.model.User;

public interface CartService {
    public Cart createCart(User user);

    public CartItem addCartItem(String userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(String userId);

    public RemoveResponse removeItem(String cartItemId , String userId);

    public RemoveResponse clearCart(String userId);


}
