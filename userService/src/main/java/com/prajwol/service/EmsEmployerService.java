package com.prajwol.service;

import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployer;

import java.util.Optional;

public interface EmsEmployerService {
    public EmsEmployer registerEmployer(EmsEmployer em);
    public Optional<EmsEmployer> getEmployerbyId(long id);
    public void deleteEmployer(Long employerId);

    UserAuthResDto loginEmployer(UserAuthReqDto employerReqDto);
}
