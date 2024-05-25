package com.prajwol.service;

import com.prajwol.dto.UserReqDto;
import com.prajwol.dto.UserResDto;
import com.prajwol.entity.EmsEmployee;

import java.util.Optional;

public interface EmsEmployeeService {
    public EmsEmployee registerEmployee(EmsEmployee em);
    public Optional<EmsEmployee> getEmployeebyId(long id);
    public void deleteEmployee(Long employerId);

    UserResDto loginEmployee(UserReqDto userReqDto);
}
