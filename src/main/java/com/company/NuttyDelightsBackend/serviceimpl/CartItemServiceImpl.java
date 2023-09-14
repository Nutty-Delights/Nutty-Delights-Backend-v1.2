package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.exception.CartItemException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.CartItem;
import com.company.NuttyDelightsBackend.model.Product;
import com.company.NuttyDelightsBackend.model.Variants;
import com.company.NuttyDelightsBackend.repository.CartItemRepository;
import com.company.NuttyDelightsBackend.repository.CartRepository;
import com.company.NuttyDelightsBackend.repository.ProductRepository;
import com.company.NuttyDelightsBackend.service.CartItemService;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private  CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public CartItem createCartItem(CartItem cartItem ) {
//        cartItem.setCartItemQuantity(cartItem.getCartItemQuantity());
//
//        cartItem.setCartItemPrice(product.get*cartItem.getQuantity());
//        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
//
//        CartItem createdCartItem=cartItemRepository.save(cartItem);
//
//        return createdCartItem;
        return null;
    }

    @Override
    public CartItem updateCartItem(String userId, String id, CartItem cartItem) throws CartItemException, UserException {
        return null;
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, Variants variant, String userId) {

        Product isPresentProduct = productRepository.findById(product.getProductId()).get();
        System.out.println("Is Present Product:" +  isPresentProduct);
        if(isPresentProduct == null){
            return  null;
        }

        CartItem cartItem = cartItemRepository.findByCartItemVariantIdAndCartItemUserId(variant.getId() , userId);
        if(cartItem != null)
            return cartItem;


        return null;


    }

    @Override
    public void removeCartItem(String userId, String cartItemId) throws CartItemException, UserException {

    }

    @Override
    public CartItem findCartItemById(String cartItemId) throws CartItemException {
        return null;
    }
}
