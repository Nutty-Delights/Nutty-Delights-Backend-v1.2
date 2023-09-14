package com.company.NuttyDelightsBackend.dto.request;

import com.company.NuttyDelightsBackend.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteration {
    private User user;
    private List<AddItemRequest> localCartItems = new ArrayList<>();
}
