package com.company.NuttyDelightsBackend.dto.response;

import com.company.NuttyDelightsBackend.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayOnDeliveryResponse {
    private boolean error;
    private String message;
    private Order order;

}
