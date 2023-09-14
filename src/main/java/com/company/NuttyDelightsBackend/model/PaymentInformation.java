package com.company.NuttyDelightsBackend.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data
@Document(collection = "Payment_Information")
public class PaymentInformation {

    private String userId;
    private String cardHolderName;
    private String cardNumber;
    private LocalDate expirationDate;
    private  String cvv;
}
