package com.company.NuttyDelightsBackend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Confirmation_Token")
public class ConfirmationToken {

    @Id
    private String tokenId;
    private  String confirmationToken;
    private Date createdData;
    private String email;
    private String userId;


    public ConfirmationToken(String userId) {

        Random r = new Random( System.currentTimeMillis() );
        this.userId = userId;
        this.createdData = new Date();
        confirmationToken = String.valueOf(((1 + r.nextInt(2)) * 10000 + r.nextInt(10000)));
    }
}
