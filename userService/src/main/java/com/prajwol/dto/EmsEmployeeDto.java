package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmsEmployeeDto {
    private long id;
    private String password;
    private String username;
    private String phone;
    private Instant joinedDate;
    private String role;
    private String employerId;
    private String departmentId;
}
