package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsDepartmentRepo;
import com.prajwol.repository.EmsEmployeeRepo;
import com.prajwol.repository.EmsEmployerRepo;
import com.prajwol.userservice.IdObfuscationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmsEmployeeServiceImpl implements EmsEmployeeService {
    private EmsEmployeeRepo emsEmployeeRepo;
    private EmsEmployerRepo emsEmployerRepo;
    private PasswordEncoder passwordEncoder;
    private EmsDepartmentRepo emsDepartmentRepo;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask ;
    @Autowired
    public EmsEmployeeServiceImpl(@Qualifier("employeeAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployeeRepo emsEmployeeRepo, EmsEmployerRepo emsEmployerRepo,  EmsDepartmentRepo emsDepartmentRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, IdObfuscationService idObfuscationService) {
        this.emsEmployeeRepo = emsEmployeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.idObfuscationService = idObfuscationService;
        this.emsEmployerRepo = emsEmployerRepo;
        this.emsDepartmentRepo = emsDepartmentRepo;
        this.idMask = idObfuscationService.idMask();
    }


    public EmsEmployee registerEmployee(EmsEmployee em) {
        em.setPassword(passwordEncoder.encode(em.getPassword()));
        EmsEmployee e = emsEmployeeRepo.save(em);
        log.info("Employee registered successfully");
        return e;
    }

    public Optional<EmsEmployee> getEmployeebyId(long id) {
        return emsEmployeeRepo.findById(id);
    }

    public void deleteEmployee(Long employerId) {
        emsEmployeeRepo.deleteById(employerId);
    }

    @Override
    public EmsEmployee createEmployee(EmsEmployeeDto em) {
        EmsEmployee e = new EmsEmployee().builder()
                .username(em.getUsername())
                .password(passwordEncoder.encode(em.getPassword()))
                .phone(em.getPhone())
                .joinedDate(Instant.now())
                .role(EmsRole.EMPLOYEE)
                .build();
        // setting employer
        if(!em.getEmployerId().isEmpty()){
            Long employerId = idMask.unmask(em.getEmployerId());
            Optional<EmsEmployer> emsEmployer = emsEmployerRepo.findById(employerId);
            emsEmployer.ifPresent(e::setEmployerDetails);
        }
        if(!em.getDepartmentId().isEmpty()){
            Long departmentId = idMask.unmask(em.getDepartmentId());
            Optional<EmsDepartment> departmentById = emsDepartmentRepo.findById(departmentId);
            departmentById.ifPresent(e::setEmsDepartment);
        }
        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Employee registered successfully");
        return savedEmployee;
    }

    @Override
    public UserAuthResDto loginEmployee(UserAuthReqDto userReqDto) {
        UserAuthResDto employeeResDto = new UserAuthResDto();
        try {
            HashMap<String, String> role = new HashMap<>();
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userReqDto.getUsername(),
                            userReqDto.getPassword()));
            var user = emsEmployeeRepo.findByUsername(userReqDto.getUsername()).orElseThrow(() -> new EmsCustomException("User Not Found", "404"));
            role.put("role", user.getRole().name());

            var jwt = jwtTokenProvider.generateToken(user.getUsername(), role);
            var refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername(), role);
            employeeResDto.setStatusCode(HttpStatus.OK.value());
            employeeResDto.setToken(jwt);
            employeeResDto.setRefreshToken(refreshToken);
            employeeResDto.setUsername(user.getUsername());
            employeeResDto.setEmployerId(idMask.mask(user.getId()));
            employeeResDto.setRole(user.getRole().name());
        } catch (Exception e) {
            employeeResDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return employeeResDto;
    }

    @Override
    public List<EmsEmployee> getAllEmployees(String employerId) {
       return  emsEmployeeRepo.findByEmployerDetailsId(idMask.unmask(employerId));
    }
}
