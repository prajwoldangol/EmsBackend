package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthResDto {

    private int statusCode;
    private String employerId;
    private String token;
    private String refreshToken;
    private String role;
    private String username;
}
