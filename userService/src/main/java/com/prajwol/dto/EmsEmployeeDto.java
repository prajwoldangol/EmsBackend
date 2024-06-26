package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsEmployeeDto {
    private String id;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private Instant joinedDate;
    private String role;
    private String employerId;
    private String departmentId;
    private EmsUserDetailDto userDetailDto;
}
