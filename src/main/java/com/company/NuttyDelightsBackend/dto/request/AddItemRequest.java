package com.company.NuttyDelightsBackend.dto.request;


import com.company.NuttyDelightsBackend.model.Variants;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddItemRequest {
    private String productId;
    private Variants variant;
//    private String gms;
    private int quantity;
//    private Integer price;
}
