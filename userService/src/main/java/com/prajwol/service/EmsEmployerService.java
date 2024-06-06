package com.prajwol.service;

import com.prajwol.dto.*;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomException;

import java.util.Optional;

public interface EmsEmployerService {
    public EmsEmployerResponseDto registerEmployer(EmsEmployerRequestDto em);
    public Optional<EmsEmployerResponseDto> getEmployerbyId(String id);
    public Optional<EmsEmployer> getById(Long id);
    public void deleteEmployer(String employerId);
    UserAuthResDto loginEmployer(UserAuthReqDto employerReqDto);

    public EmsEmployerResponseDto updateEmployerPassword(String empId, String newPassword) throws EmsCustomException;
    public EmsEmployerResponseDto updateEmployer(String empId, EmsEmployerRequestDto em) throws EmsCustomException;
    public EmsEmployerResponseDto updateEmployeeRole(String empId, EmsRole role) throws EmsCustomException;
    public boolean generateToken(String empId);
    public boolean verifyTokenAndUpdatePassword(String userId, EmsTokenDto emsTokenDto) throws EmsCustomException;
}
