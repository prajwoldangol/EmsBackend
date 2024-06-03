package com.prajwol.dto;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmsEmployeeClockDto {
    private String employerId;
    private Instant clockInTime;
}
