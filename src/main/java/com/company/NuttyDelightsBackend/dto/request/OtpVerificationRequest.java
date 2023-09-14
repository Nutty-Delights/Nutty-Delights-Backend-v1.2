package com.company.NuttyDelightsBackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {
    private  String jwt;
    private String otp;
    private String email;

}
