package com.company.NuttyDelightsBackend.model;

import com.company.NuttyDelightsBackend.domain.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "Orders")

public class Order {
    @Id
    private String id;

    private String orderId;

    private User user;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private  Address shippingAddress;
    private  Double totalPrice;
    private Double discount;
    private int totalItems;
    private LocalDateTime createdAt;

    private List<OrderItem> orderItems = new ArrayList<>();

    private PaymentDetails paymentDetails=new PaymentDetails();
    private OrderStatus orderStatus;






}
