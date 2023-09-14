package com.company.NuttyDelightsBackend.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private  String jwt;
    private String message;
}
