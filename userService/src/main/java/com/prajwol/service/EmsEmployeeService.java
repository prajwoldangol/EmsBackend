package com.prajwol.service;

import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.exception.EmsCustomException;

import java.util.List;
import java.util.Optional;

public interface EmsEmployeeService {
    public EmsEmployee registerEmployee(EmsEmployee em);
    public Optional<EmsEmployee> getEmployeebyId(long id);
    public void deleteEmployee(Long employerId);
    public EmsEmployee createEmployee(EmsEmployeeDto em);
    UserAuthResDto loginEmployee(UserAuthReqDto userReqDto);
    public List<EmsEmployee> getAllEmployees(String employerId);
    public void createEmployeeKafka(EmsEmployeeDto em);
    public EmsEmployee updateEmployee(String empId, EmsEmployeeDto em) throws EmsCustomException;
    public EmsEmployee updateEmployeePassword(String empId, String newPassword) throws EmsCustomException;
}
