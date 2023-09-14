package com.company.NuttyDelightsBackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpGenerationRequest {
    private  String jwt;
    private  String email;
}
