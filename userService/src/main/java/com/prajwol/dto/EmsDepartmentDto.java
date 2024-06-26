package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsDepartmentDto {
    private String id;
    private String name;
    private String employerId;
    private List<EmsDepartmentEmployeeDto> employees;
}
