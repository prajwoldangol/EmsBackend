package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmsScheduleDto {
    private ObjectId id;
    private String employerId;
    private String departmentId;
    private Instant startDate;
    private Instant endDate;
    private Map<Instant, List<EmsEmployeeScheduleDto>> employeeSchedules;
}
