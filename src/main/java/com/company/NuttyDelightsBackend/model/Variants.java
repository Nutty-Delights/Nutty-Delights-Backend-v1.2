package com.company.NuttyDelightsBackend.model;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Variants {


    String id;
    private String weight;
    private int quantity;
    private Double sellingPrice;
    private Double discount;

    public Variants(){
        UUID uuid=UUID.randomUUID();
        this.id = uuid.toString();

    }

}
