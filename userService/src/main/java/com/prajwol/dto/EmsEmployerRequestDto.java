package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsEmployerRequestDto {
    private String id ;
    private String username;
    private String password;
    private String companyName;
    private String phone;
    private String role;
    private EmsUserDetailDto userDetailDto;
}
