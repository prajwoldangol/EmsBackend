package com.prajwol.dto.dtoservice;

import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.entity.EmsRole;

public class EmsEmployeeConverter {
    public static EmsEmployeeDto toDto(EmsEmployee entity) {
        EmsEmployeeDto dto = new EmsEmployeeDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setJoinedDate(entity.getJoinedDate());
        dto.setRole(entity.getRole().toString());
        if (entity.getEmployerDetails() != null) {
            dto.setEmployerId(String.valueOf(entity.getEmployerDetails().getId()));
        }
        if (entity.getEmsDepartment() != null) {
            dto.setDepartmentId(String.valueOf(entity.getEmsDepartment().getId()));
        }
        return dto;
    }

    public static EmsEmployee toEntity(EmsEmployeeDto dto) {
        EmsEmployee entity = new EmsEmployee();
        entity.setId(dto.getId());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setJoinedDate(dto.getJoinedDate());
        entity.setRole(EmsRole.EMPLOYEE);
        // You may need to handle employer and department entities here if needed
        return entity;
    }
}
