package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.EmsEmailDto;
import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.*;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsDepartmentRepo;
import com.prajwol.repository.EmsEmployeeRepo;
import com.prajwol.repository.EmsEmployerRepo;
import com.prajwol.userservice.IdObfuscationService;
import com.prajwol.utility.FieldUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
    private KafkaTemplate<String, EmsEmailDto> kafkaTemplate;
    @Autowired
    public EmsEmployeeServiceImpl(@Qualifier("employeeAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployeeRepo emsEmployeeRepo, EmsEmployerRepo emsEmployerRepo,  EmsDepartmentRepo emsDepartmentRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,KafkaTemplate<String, EmsEmailDto> kafkaTemplate, IdObfuscationService idObfuscationService) {
        this.emsEmployeeRepo = emsEmployeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.idObfuscationService = idObfuscationService;
        this.emsEmployerRepo = emsEmployerRepo;
        this.emsDepartmentRepo = emsDepartmentRepo;
        this.idMask = idObfuscationService.idMask();
        this.kafkaTemplate = kafkaTemplate;
    }


    public EmsEmployee registerEmployee(EmsEmployee em) {
        em.setPassword(em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : passwordEncoder.encode(em.getPhone()));
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
    @Transactional
    public EmsEmployee createEmployee(EmsEmployeeDto em) {
        EmsEmployee e = new EmsEmployee().builder()
                .username(em.getUsername())
                .password(em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : passwordEncoder.encode(em.getPhone()))
                .phone(em.getPhone())
                .joinedDate(Instant.now())
                .role(EmsRole.EMPLOYEE)
                .build();
        // setting employer
        if( em.getEmployerId() != null && !em.getEmployerId().isEmpty()){
            Long employerId = idMask.unmask(em.getEmployerId());
            Optional<EmsEmployer> emsEmployer = emsEmployerRepo.findById(employerId);
            emsEmployer.ifPresent(e::setEmployerDetails);
        }
        if(em.getDepartmentId() !=null && !em.getDepartmentId().isEmpty()){
            Long departmentId = idMask.unmask(em.getDepartmentId());
            Optional<EmsDepartment> departmentById = emsDepartmentRepo.findById(departmentId);
            departmentById.ifPresent(e::setEmsDepartment);
        }
        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Employee registered successfully");
        return savedEmployee;
    }

    @Override
    @Transactional
    public EmsEmployee updateEmployee(String empId, EmsEmployeeDto em) throws EmsCustomException {
        EmsEmployee e = emsEmployeeRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + em.getId() + " not found.", "404"));

        // Updating fields using FieldUtils
        FieldUtils.updateFieldIfPresent(em.getUsername(), e::setUsername, FieldUtils.NOT_EMPTY_STRING);
        String encodedPassword = em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : passwordEncoder.encode(em.getPhone());
        FieldUtils.updateFieldIfPresent(encodedPassword, e::setPassword, Objects::nonNull);
        FieldUtils.updateFieldIfPresent(em.getPhone(), e::setPhone, FieldUtils.NOT_EMPTY_STRING);

        // Optionally update employer details if included
        if (em.getEmployerId() != null && !em.getEmployerId().isEmpty()) {
            Long employerId = idMask.unmask(em.getEmployerId());
            emsEmployerRepo.findById(employerId).ifPresent(e::setEmployerDetails);
        }

        // Update department details if provided
        if (em.getDepartmentId() != null && !em.getDepartmentId().isEmpty()) {
            Long departmentId = idMask.unmask(em.getDepartmentId());
            emsDepartmentRepo.findById(departmentId).ifPresent(e::setEmsDepartment);
        }
        // Update user details if provided
        if (em.getUserDetailDto() != null) {
            EmsUserDetails userDetails = EmsUserDetails.builder()
                    .streetAddress(em.getUserDetailDto().getStreetAddress())
                    .unitNumber(em.getUserDetailDto().getUnitNumber())
                    .city(em.getUserDetailDto().getCity())
                    .state(em.getUserDetailDto().getState())
                    .zipCode(em.getUserDetailDto().getZipCode())
                    .ssnOrTin(em.getUserDetailDto().getSsnOrTin())
                    .build();
            e.setEmsUserDetails(userDetails);
        }

        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Employee with id " + em.getId() + " updated successfully");
        return savedEmployee;
    }

    @Override
    @Transactional
    public EmsEmployee updateEmployeePassword(String empId, String newPassword) throws EmsCustomException {
        EmsEmployee e = emsEmployeeRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + empId + " not found.", "404"));

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        e.setPassword(encodedPassword);

        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Password for employee with id " + empId + " updated successfully");
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

    @Override
    public void createEmployeeKafka(EmsEmployeeDto em) {
        EmsEmployee employee = createEmployee(em);
        EmsEmailDto emailData = new EmsEmailDto().builder()
                .to(employee.getUsername())
                .subject("Your new Account info")
                .body("Your username is " + employee.getUsername() + " Follow the link to reset password http://localhost:5173/reset-password?id=" + idMask.mask(employee.getId()))
                .build();
        kafkaTemplate.send("send-employee-email", emailData);
    }
}
