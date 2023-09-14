package com.company.NuttyDelightsBackend.model;


import com.company.NuttyDelightsBackend.domain.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    private  String userId;
    private  String firstName;
    private String lastName="";

    @NonNull
    private  String email;

    private  String password;
    private UserRole role;
    private Boolean isEnabled = false;
    private String mobileNumber;
    private List<Address> address = new ArrayList<>();
    private List<PaymentInformation> paymentInformation= new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
