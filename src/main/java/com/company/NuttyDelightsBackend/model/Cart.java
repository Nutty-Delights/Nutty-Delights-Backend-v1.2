package com.company.NuttyDelightsBackend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "Cart")
public class Cart {

    @Id
    private  String cartId;
    private String cartUserId = "";
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private Double cartTotalPrice = 0.0;
    private Integer cartTotalItems = 0;






}
