package com.company.NuttyDelightsBackend.controller.Cart;


import com.company.NuttyDelightsBackend.dto.request.AddItemRequest;
import com.company.NuttyDelightsBackend.dto.response.CartItemResponseDTO;
import com.company.NuttyDelightsBackend.dto.response.RemoveResponse;
import com.company.NuttyDelightsBackend.exception.ProductException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.CartItem;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{

        User user=userService.findUserProfileByJwtToken(jwt);

        Cart cart=cartService.findUserCart(user.getUserId());

//        System.out.println("cart - "+cart.getUser().getEmail());


        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }


    @PutMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwtToken(jwt);

        try{
            CartItem cartItem = cartService.addCartItem(user.getUserId(), req);
            CartItemResponseDTO res= new CartItemResponseDTO( true,"Item Added To Cart Successfully",cartItem  );
            return new ResponseEntity<CartItemResponseDTO>(res, HttpStatus.ACCEPTED);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO(false, "Something went wrong" , null);
            return new ResponseEntity<CartItemResponseDTO>( cartItemResponseDTO ,HttpStatus.ACCEPTED);
        }



    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<RemoveResponse> removeItem(@PathVariable("cartItemId")  String cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwtToken(jwt);

        try{
            return new ResponseEntity<RemoveResponse>(cartService.removeItem(cartItemId , user.getUserId()), HttpStatus.OK);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<RemoveResponse>( new RemoveResponse(false , null) ,HttpStatus.OK);
        }

    }



}
