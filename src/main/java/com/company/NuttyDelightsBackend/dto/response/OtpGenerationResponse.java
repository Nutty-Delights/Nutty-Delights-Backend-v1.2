package com.company.NuttyDelightsBackend.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpGenerationResponse {
    private String otp;
    private boolean error;
    private String message;
}
