package com.prajwol.dto.dtoservice;

import com.prajwol.dto.*;
import com.prajwol.entity.*;
import com.prajwol.userservice.IdObfuscationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmsEntityDtoConverter {
    public static IdObfuscationService idObfuscationService = new IdObfuscationService();
    public static EmsEmployeeDto toDto(EmsEmployee entity) {
        if (entity == null) {
            return null;
        }
        EmsEmployeeDto dto = new EmsEmployeeDto().builder()
                .id(idObfuscationService.idMask().mask(entity.getId()))
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .role(entity.getRole().name())
                .joinedDate(entity.getJoinedDate())
                .build();
        if (entity.getEmployerDetails() != null) {
            dto.setEmployerId(idObfuscationService.idMask().mask(entity.getEmployerDetails().getId()));
        }
        if (entity.getEmsDepartment() != null) {
            dto.setDepartmentId(String.valueOf(idObfuscationService.idMask().mask(entity.getEmsDepartment().getId())));
        }
        EmsUserDetails userDetails = entity.getEmsUserDetails();
        if(userDetails != null) {
//            EmsUserDetailDto userDetailDto = new EmsUserDetailDto().builder()
//                    .city(userDetails.getCity())
//                    .state(userDetails.getState())
//                    .zipCode(userDetails.getZipCode())
//                    .streetAddress(userDetails.getStreetAddress())
//                    .unitNumber(userDetails.getUnitNumber())
//                    .build();
            dto.setUserDetailDto(mapUserDetailsDto(userDetails));
        }
        return dto;
    }

    public static EmsEmployee toEntity(EmsEmployeeDto dto) {
        EmsEmployee entity = new EmsEmployee();
        entity.setId(idObfuscationService.idMask().unmask(dto.getId()));
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setJoinedDate(dto.getJoinedDate());
        entity.setRole(EmsRole.EMPLOYEE);

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

    public static EmsEmployerResponseDto toEmployerDto(EmsEmployer entity){
        if (entity == null) {
            return null;
        }
        return EmsEmployerResponseDto.builder()
                .id(idObfuscationService.idMask().mask(entity.getId()))
                .username(entity.getUsername())
                .companyName(entity.getCompanyName())
                .phone(entity.getPhone())
                .joinedDate(entity.getSignedUpDate())
                .role(entity.getRole().toString())
                .emsEmployerEmpDtos(Optional.ofNullable(entity.getEmsEmployee())
                        .map(EmsEntityDtoConverter::mapEmployeeDto)
                        .orElse(null))
                .emsEmployerDeptDtoList(Optional.ofNullable(entity.getDepartments())
                        .map(EmsEntityDtoConverter::mapDepartmentDto)
                        .orElse(null))
                .emsSubscriptionsDtos(Optional.ofNullable(entity.getEmsSubscriptionsList())
                        .map(EmsEntityDtoConverter::mapSubscriptionDto)
                        .orElse(null))
                .userDetailDto(Optional.ofNullable(entity.getEmsUserDetails())
                        .map(EmsEntityDtoConverter::mapUserDetailsDto)
                        .orElse(null))
                .build();
    }
    private static EmsUserDetailDto mapUserDetailsDto(EmsUserDetails entity){
        return EmsUserDetailDto.builder()
                .streetAddress(entity.getStreetAddress())
                .unitNumber(entity.getUnitNumber())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
//                .ssnOrTin(entity.getSsnOrTin())
                .build();
    }
    private static List<EmsEmployerEmpDto> mapEmployeeDto(List<EmsEmployee> employees) {
        return employees.stream()
                .map(employee -> EmsEmployerEmpDto.builder()
                        .id(idObfuscationService.idMask().mask(employee.getId()))
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .joinedDate(employee.getJoinedDate())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<EmsEmployerDeptDto> mapDepartmentDto(List<EmsDepartment> departments) {
        return departments.stream()
                .map(department -> EmsEmployerDeptDto.builder()
                        .id(idObfuscationService.idMask().mask(department.getId()))
                        .name(department.getName())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<EmsSubscriptionsDto> mapSubscriptionDto(List<EmsSubscriptions> subscriptions) {
        return subscriptions.stream()
                .map(subscription -> EmsSubscriptionsDto.builder()
                        .id(idObfuscationService.idMask().mask(subscription.getId()))
                        .paidOn(subscription.getPaidOn())
                        .expiringOn(subscription.getExpiringOn())
                        .payCycle(subscription.getPayCycle())
                        .paymentAmount(subscription.getPaymentAmount())
                        .stripeCustomerId(subscription.getStripeCustomerId())
                        .stripeInvoiceId(subscription.getStripeInvoiceId())

//                        .stripePayIntentId(subscription.getStripePayIntentId())
//                        .stripeLatestCharge(subscription.getStripeLatestCharge())
                        .build())
                .collect(Collectors.toList());
    }
    public static EmsUserDetails emsUserDetailsDtoToEntity(EmsUserDetailDto dto) {
        if (dto == null) {
            return null;
        }

        return EmsUserDetails.builder()
                .streetAddress(dto.getStreetAddress())
                .unitNumber(dto.getUnitNumber())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .ssnOrTin(dto.getSsnOrTin())
                .build();
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
