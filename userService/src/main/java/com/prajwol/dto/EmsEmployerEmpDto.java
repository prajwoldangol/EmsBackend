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
public class EmsEmployerEmpDto {
    private String id;
    private String firstName;
    private String lastName;
    private Instant joinedDate;
}
