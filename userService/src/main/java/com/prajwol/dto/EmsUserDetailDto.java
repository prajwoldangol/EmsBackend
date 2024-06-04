package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsUserDetailDto {

    private String streetAddress;
    private String unitNumber;
    private String city;
    private String state;
    private String zipCode;
    private String ssnOrTin;
}
