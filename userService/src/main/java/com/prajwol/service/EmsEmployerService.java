package com.prajwol.service;

import com.prajwol.dto.EmsTokenDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomException;

import java.util.Optional;

public interface EmsEmployerService {
    public EmsEmployer registerEmployer(EmsEmployer em);
    public Optional<EmsEmployer> getEmployerbyId(long id);
    public void deleteEmployer(Long employerId);
    UserAuthResDto loginEmployer(UserAuthReqDto employerReqDto);

    public EmsEmployer updateEmployerPassword(String empId, String newPassword) throws EmsCustomException;
    public EmsEmployer updateEmployer(String empId, EmsEmployer em) throws EmsCustomException;
    public EmsEmployer updateEmployeeRole(String empId, EmsRole role) throws EmsCustomException;
    public boolean generateToken(String empId);
    public boolean verifyTokenAndUpdatePassword(String userId, EmsTokenDto emsTokenDto) throws EmsCustomException;
}
