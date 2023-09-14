package com.company.NuttyDelightsBackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationResponse {
    private String message;
    private Boolean success;
}
