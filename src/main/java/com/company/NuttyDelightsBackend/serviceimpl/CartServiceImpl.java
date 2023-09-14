package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.dto.request.AddItemRequest;
import com.company.NuttyDelightsBackend.dto.request.CartDTO;
import com.company.NuttyDelightsBackend.dto.request.CartItemDTO;
import com.company.NuttyDelightsBackend.dto.response.CartItemResponseDTO;
import com.company.NuttyDelightsBackend.dto.response.CartResponseDTO;
import com.company.NuttyDelightsBackend.dto.response.RemoveResponse;
import com.company.NuttyDelightsBackend.exception.ProductException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.CartItem;
import com.company.NuttyDelightsBackend.model.Product;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.repository.CartItemRepository;
import com.company.NuttyDelightsBackend.repository.CartRepository;
import com.company.NuttyDelightsBackend.service.CartItemService;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.ProductService;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

//    @Autowired
//    private UserService userService;


    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setCartUserId(user.getUserId());
        Cart createdCart =cartRepository.save(cart);
        return createdCart;

    }


    @Override
    public CartItem addCartItem(String userId, AddItemRequest req) throws ProductException {

        System.out.println("User ID," + userId + ",");
        Cart cart= cartRepository.findByCartUserId(userId);
        System.out.println("cart," + cart);
        Product product =productService.getProductById(req.getProductId());
        CartItem isPresentCartItem = cartItemService.isCartItemExist( cart , product , req.getVariant() , userId );

        System.out.println("isPresent" + " " + isPresentCartItem);
        if(isPresentCartItem == null) {
            CartItem cartItem = new CartItem();
            cartItem.setCartItemQuantity(req.getQuantity());
            cartItem.setCartItemCartId(cart.getCartId() );
            cartItem.setCartItemQuantity(req.getQuantity());
            cartItem.setCartItemUserId(userId);
            cartItem.setCartItemVariant(req.getVariant());
            Double price = req.getVariant().getSellingPrice()*req.getQuantity();
            cartItem.setCartItemPrice(price);
            cartItem.setCartItemProduct(product);
//            CartItem createdCartItem=cartItemService.createCartItem(cartItem);

            CartItem createdCartItem = cartItemRepository.save(cartItem);
            System.out.println("Created cart item," + createdCartItem);
//            Boolean a =  cart.getCartItems().add(createdCartItem);
//            System.out.println("a:" + a + " " + cart.getCartItems().toArray().length    );
//            cart.setCartItems(cart.getCartItems().add(createdCartItem));
            cart.getCartItems().add(createdCartItem);
            cart.setCartTotalItems(cart.getCartItems().size() );

            cart.setCartTotalPrice(cart.getCartTotalPrice() + createdCartItem.getCartItemPrice());
            System.out  .println("cart after new item:"+cart) ;
            cartRepository.save(cart);
            return  createdCartItem;

        }


            isPresentCartItem.setCartItemQuantity(isPresentCartItem.getCartItemQuantity() + req.getQuantity());
            Double price = isPresentCartItem.getCartItemPrice() + req.getVariant().getSellingPrice()*req.getQuantity(); ;
            isPresentCartItem.setCartItemPrice(price);

            CartItem added =  cartItemRepository.save(isPresentCartItem);
            ArrayList<CartItem> cartItems =  cart.getCartItems();

            Double totalPrice = 0.0;
            for(int i = 0 ; i < cartItems.size() ; i++)
            {
                if(cartItems.get(i).getCartItemVariant().getId().equals(req.getVariant().getId()))
                {
                    cartItems.set(i, added);
                }

                totalPrice += cartItems.get(i).getCartItemPrice();
            }
        cart.setCartTotalPrice(totalPrice);
        cartRepository.save(cart);




        return  added;


    }



    @Override
    public Cart findUserCart(String userId) {
        Cart cart = cartRepository.findByCartUserId(userId);
        return cart;
    }

    @Override
    public RemoveResponse removeItem(String cartItemId, String userId) {
        try{
            cartItemRepository.deleteById(cartItemId);
            Cart cart = cartRepository.findByCartUserId(userId);
            ArrayList<CartItem> cartItems =  new ArrayList<>();
            System.out.println("before cartItems:" +cartItems);
            Double price = 0.0;

            for(CartItem cartItem : cart.getCartItems())
            {
                if(!cartItem.getCartItemId().equals(cartItemId))
                {
                    cartItems.add(cartItem);
                    price += cartItem.getCartItemPrice();
                }
            }

            System.out.println("Updated cart Items" + cartItems);
            System.out.println("Price:" + price);

            cart.setCartItems(cartItems);
            cart.setCartTotalPrice(price);
            cart.setCartTotalItems(cartItems.size());
             Cart newCart = cartRepository.save(cart);
            System.out.println("newCart:" + newCart);

            return new RemoveResponse(true , cart);
        }
        catch (Error e)
        {
            e.printStackTrace();
            return new RemoveResponse(false , null);
        }
    }

    @Override
    public RemoveResponse clearCart(String userId) {

        Cart cart = cartRepository.findByCartUserId(userId);
        try {

            cart.setCartTotalItems(0);
            cart.setCartTotalPrice(0.0);
            List<CartItem> cartItemList = cart.getCartItems();
            for(CartItem cartItem : cartItemList)
            {
                cartItemRepository.deleteById(cartItem.getCartItemId());
            }
            cart.setCartItems(new ArrayList<>());
            cartRepository.save(cart);


            return new RemoveResponse(true , cart);
        }

        catch (Error e)
        {
            e.printStackTrace();
            return  new RemoveResponse(false ,  cart);
        }
    }
}
