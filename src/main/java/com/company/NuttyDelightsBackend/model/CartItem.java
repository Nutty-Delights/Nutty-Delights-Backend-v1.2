package com.company.NuttyDelightsBackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "Cart_Items")
public class CartItem  {


    @Id
    private  String cartItemId;
    private  String cartItemUserId;
    private String  cartItemCartId;

    private  Variants cartItemVariant;

    private  Product cartItemProduct;
    private Integer cartItemQuantity = 1;
    private Double cartItemPrice;


    @Override
    public int hashCode() {
        return Objects.hash(cartItemId , cartItemVariant  , cartItemPrice );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        return Objects.equals(cartItemId, other.cartItemId) && Double.doubleToLongBits(cartItemPrice) == Double.doubleToLongBits(other.cartItemPrice)
                && Objects.equals(cartItemVariant, other.cartItemVariant) ;
    }





}
