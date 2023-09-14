package com.company.NuttyDelightsBackend.dto.request;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CartDTO {
    private String cartUserId;
    private Double cartTotalPrice ;
    private Integer cartTotalItems ;
}
