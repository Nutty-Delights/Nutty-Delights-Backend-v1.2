package com.company.NuttyDelightsBackend.dto.response;


import com.company.NuttyDelightsBackend.model.CartItem;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItemResponseDTO {
    private Boolean status;
    private  String message;
    private CartItem cartItem;
}
