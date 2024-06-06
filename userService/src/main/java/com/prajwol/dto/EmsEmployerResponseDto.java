package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsEmployerResponseDto {
    private String id;
    private String username;
    private String companyName;
    private String phone;
    private Instant joinedDate;
    private String role;
    private List<EmsEmployerEmpDto> emsEmployerEmpDtos;
    private List<EmsEmployerDeptDto> emsEmployerDeptDtoList;
    private List<EmsSubscriptionsDto> emsSubscriptionsDtos;
    private EmsUserDetailDto userDetailDto;
}
