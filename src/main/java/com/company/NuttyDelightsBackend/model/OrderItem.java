package com.company.NuttyDelightsBackend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // important
@Document(collection = "Order_Items")

public class OrderItem {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String order;
    private Product product;
    private Variants variant;

    private  int quantity;
    private   Double price;
    private String userId;

    private LocalDateTime deliveryDate;

    @Override
    public int hashCode() {
        return Objects.hash(id, order, price, product, quantity, variant, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        return Objects.equals(id, other.id) && Objects.equals(order, other.order) && Objects.equals(price, other.price)
                && Objects.equals(product, other.product) && quantity == other.quantity
                && Objects.equals(variant, other.variant) && Objects.equals(userId, other.userId);
    }





}
