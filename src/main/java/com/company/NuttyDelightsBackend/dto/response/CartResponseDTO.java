package com.company.NuttyDelightsBackend.dto.response;

import com.company.NuttyDelightsBackend.model.Cart;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private  String cartId;
    private String cartUserId;
    private Double cartTotalPrice = 0.0;
    private Integer cartTotalItems = 0;
    private String responseMessage;

    public CartResponseDTO(Cart cart , String message){
        this.cartId = cart.getCartId();
        this.cartUserId = cart.getCartUserId();
        this.cartTotalItems = cart.getCartTotalItems();
        this.cartTotalPrice = cart.getCartTotalPrice();
        this.responseMessage = message;
    } public CartResponseDTO(String message){
        this.responseMessage = message;
    }
}
