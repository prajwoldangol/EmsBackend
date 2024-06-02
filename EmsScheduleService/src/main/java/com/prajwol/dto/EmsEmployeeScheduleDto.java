package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmsEmployeeScheduleDto {
    private String employeeId;
    private Instant startTime;
    private Instant breakTime;
    private Instant endTime;
}
