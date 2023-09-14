package com.company.NuttyDelightsBackend.dto.request;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CartItemDTO {
    private String  cartItemCartId;
    private Integer cartItemQuantity = 1;

    private  String cartItemProductId;
}
