package com.company.NuttyDelightsBackend.dto.response;

import com.company.NuttyDelightsBackend.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveResponse {
    private Boolean status;
    private Cart cart;
}
