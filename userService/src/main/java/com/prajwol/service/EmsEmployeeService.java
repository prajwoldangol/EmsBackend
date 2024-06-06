package com.prajwol.service;

import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.dto.EmsTokenDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.entity.EmsUserToken;
import com.prajwol.exception.EmsCustomException;

import java.util.List;
import java.util.Optional;

public interface EmsEmployeeService {
    public EmsEmployeeDto registerEmployee(EmsEmployee em);
    public Optional<EmsEmployeeDto> getEmployeebyId(String id) throws EmsCustomException;
    public void deleteEmployee(String employerId);
    public EmsEmployeeDto createEmployee(EmsEmployeeDto em);
    UserAuthResDto loginEmployee(UserAuthReqDto userReqDto);
    public List<EmsEmployeeDto> getAllEmployees(String employerId);
    public EmsEmployeeDto createEmployeeKafka(EmsEmployeeDto em);
    public EmsEmployeeDto updateEmployee(String empId, EmsEmployeeDto em) throws EmsCustomException;
    public EmsEmployeeDto updateEmployeePassword(String empId, String newPassword) throws EmsCustomException;

    public boolean generateToken(String empId) throws EmsCustomException;
    public boolean verifyTokenAndUpdatePassword(String token, EmsTokenDto emsTokenDto) throws EmsCustomException;
}
