package com.prajwol.dto.dtoservice;

import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.dto.EmsDepartmentEmployeeDto;
import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.entity.EmsRole;
import com.prajwol.userservice.IdObfuscationService;

import java.util.ArrayList;
import java.util.List;

public class EmsEmployeeConverter {
    public static IdObfuscationService idObfuscationService = new IdObfuscationService();
    public static EmsEmployeeDto toDto(EmsEmployee entity) {
        EmsEmployeeDto dto = new EmsEmployeeDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setJoinedDate(entity.getJoinedDate());
        dto.setRole(entity.getRole().toString());
//        IdObfuscationService idObfuscationService = new IdObfuscationService();
        if (entity.getEmployerDetails() != null) {
            dto.setEmployerId(idObfuscationService.idMask().mask(entity.getEmployerDetails().getId()));
        }
        if (entity.getEmsDepartment() != null) {
            dto.setDepartmentId(String.valueOf(idObfuscationService.idMask().mask(entity.getEmsDepartment().getId())));
        }
//        if (entity.getEmsDepartment() != null) {
//            dto.setDepartmentId(Long.valueOf(String.valueOf(entity.getEmsDepartment().getId())));
//        }
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
//        if (dto.getEmployerDetails() != null) {
//            entity.set(idObfuscationService.idMask().mask(entity.getEmployerDetails().getId()));
//        }
//        if (dto.getEmsDepartment() != null) {
//            entity.setDepartmentId(String.valueOf(idObfuscationService.idMask().mask(entity.getEmsDepartment().getId())));
//        }
        // You may need to handle employer and department entities here if needed
        return entity;
    }

    public static EmsDepartmentDto toDtoDept(EmsDepartment entity) {
        if (entity == null) {
            return null;
        }
        String maskedEmployerId = idObfuscationService.idMask().mask(entity.getEmsEmployer().getId());
        // Create EmsDepartmentDto object
        EmsDepartmentDto dto = new EmsDepartmentDto();
        dto.setId(idObfuscationService.idMask().mask(entity.getId()));
        dto.setName(entity.getName());
        dto.setEmployerId(maskedEmployerId);
        dto.setEmployees(mapEmployees(entity.getEmsEmployeeList()));

        return dto;
    }
    private static List<EmsDepartmentEmployeeDto> mapEmployees(List<EmsEmployee> employees) {
        List<EmsDepartmentEmployeeDto> employeeDtos = new ArrayList<>();
        if (employees != null) {
            for (EmsEmployee employee : employees) {
                EmsDepartmentEmployeeDto employeeDto = new EmsDepartmentEmployeeDto();
                employeeDto.setId(idObfuscationService.idMask().mask(employee.getId())); // Mask employee id
                employeeDto.setFirstName(employee.getFirstName());
                employeeDto.setLastName(employee.getLastName());// Assuming name is a property of EmsEmployee
                employeeDtos.add(employeeDto);
            }
        }
        return employeeDtos;
    }
}
