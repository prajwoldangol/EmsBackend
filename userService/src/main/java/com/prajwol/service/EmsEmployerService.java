package com.prajwol.service;

import com.prajwol.dto.UserReqDto;
import com.prajwol.dto.UserResDto;
import com.prajwol.entity.EmsEmployer;

import java.util.Optional;

public interface EmsEmployerService {
    public EmsEmployer registerEmployer(EmsEmployer em);
    public Optional<EmsEmployer> getEmployerbyId(long id);
    public void deleteEmployer(Long employerId);

    UserResDto loginEmployer(UserReqDto employerReqDto);
}
