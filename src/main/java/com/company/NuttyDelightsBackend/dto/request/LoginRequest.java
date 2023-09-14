package com.company.NuttyDelightsBackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private  String email;
    private  String password;

    private List<AddItemRequest> localCartItem = new ArrayList<>();

}
