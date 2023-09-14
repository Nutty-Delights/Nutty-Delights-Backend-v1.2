package com.company.NuttyDelightsBackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Address")
public class Address {


    private String userId;
    @Id
    private String addressId;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String pinCode;
    private  String houseNo;
    private String mobileNumber;

}
