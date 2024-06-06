package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.*;
import com.prajwol.dto.dtoservice.EmsEntityDtoConverter;
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
import java.util.stream.Collectors;

@Service
@Log4j2
public class EmsEmployeeServiceImpl implements EmsEmployeeService {
    private final EmsEmployeeRepo emsEmployeeRepo;
    private final EmsEmployerRepo emsEmployerRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmsDepartmentRepo emsDepartmentRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask ;
    private final KafkaTemplate<String, EmsEmailDto> kafkaTemplate;
    private final EmsUserTokenService emsUserTokenService;

    @Autowired
    public EmsEmployeeServiceImpl(@Qualifier("employeeAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployeeRepo emsEmployeeRepo, EmsEmployerRepo emsEmployerRepo,  EmsDepartmentRepo emsDepartmentRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,EmsUserTokenService emsUserTokenService, KafkaTemplate<String, EmsEmailDto> kafkaTemplate, IdObfuscationService idObfuscationService) {
        this.emsEmployeeRepo = emsEmployeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.idObfuscationService = idObfuscationService;
        this.emsEmployerRepo = emsEmployerRepo;
        this.emsDepartmentRepo = emsDepartmentRepo;
        this.idMask = idObfuscationService.idMask();
        this.kafkaTemplate = kafkaTemplate;
        this.emsUserTokenService = emsUserTokenService;
    }


    public EmsEmployeeDto registerEmployee(EmsEmployee em) {
        em.setPassword(em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : passwordEncoder.encode(em.getPhone()));
        EmsEmployee e = emsEmployeeRepo.save(em);
        log.info("Employee registered successfully");
        return EmsEntityDtoConverter.toDto(e);
    }

    public Optional<EmsEmployeeDto> getEmployeebyId(String id) throws EmsCustomException {
        return Optional.ofNullable(emsEmployeeRepo.findById(idObfuscationService.idMask().unmask(id))
                .map(EmsEntityDtoConverter::toDto)
                .orElseThrow(() -> new EmsCustomException("Employee not found", "404")));
    }

    public void deleteEmployee(String employerId) {
        emsEmployeeRepo.deleteById(idObfuscationService.idMask().unmask(employerId));
    }


    @Override
    @Transactional
    public EmsEmployeeDto createEmployee(EmsEmployeeDto em) {
        EmsEmployee e = new EmsEmployee().builder()
                .username(em.getUsername())
                .password(em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : passwordEncoder.encode(em.getPhone()))
                .firstName(em.getFirstName())
                .lastName(em.getLastName())
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
//        if(em.getDepartmentId() !=null ){
//            log.info("ENTERED INSIDE DEpartment");
//            //Long departmentId = idMask.unmask(em.getDepartmentId());
//            Optional<EmsDepartment> departmentById = emsDepartmentRepo.findById(em.getDepartmentId());
//            departmentById.ifPresent(e::setEmsDepartment);
//        }
        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Employee registered successfully");
        return EmsEntityDtoConverter.toDto(savedEmployee);
    }

    @Override
    @Transactional
    public EmsEmployeeDto updateEmployee(String empId, EmsEmployeeDto em) throws EmsCustomException {
        EmsEmployee e = emsEmployeeRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + em.getId() + " not found.", "404"));

        // Updating fields using FieldUtils
        FieldUtils.updateFieldIfPresent(em.getUsername(), e::setUsername, FieldUtils.NOT_EMPTY_STRING);
        String encodedPassword = em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : e.getPassword();
        FieldUtils.updateFieldIfPresent(encodedPassword, e::setPassword, Objects::nonNull);
        FieldUtils.updateFieldIfPresent(em.getPhone(), e::setPhone, FieldUtils.NOT_EMPTY_STRING);
        FieldUtils.updateFieldIfPresent(em.getFirstName(), e::setFirstName, FieldUtils.NOT_EMPTY_STRING);
        FieldUtils.updateFieldIfPresent(em.getLastName(), e::setLastName, FieldUtils.NOT_EMPTY_STRING);
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

//        if (em.getDepartmentId() != null) {
//           // Long departmentId = idMask.unmask(em.getDepartmentId());
//            emsDepartmentRepo.findById(em.getDepartmentId()).ifPresent(e::setEmsDepartment);
//        }
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
        return EmsEntityDtoConverter.toDto(savedEmployee);
    }

    @Override
    @Transactional
    public EmsEmployeeDto updateEmployeePassword(String empId, String newPassword) throws EmsCustomException {
        EmsEmployee e = emsEmployeeRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + empId + " not found.", "404"));

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        e.setPassword(encodedPassword);

        EmsEmployee savedEmployee = emsEmployeeRepo.save(e);
        log.info("Password for employee with id " + empId + " updated successfully");
        return EmsEntityDtoConverter.toDto(savedEmployee);
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
    public List<EmsEmployeeDto> getAllEmployees(String employerId) {
        List<EmsEmployee> employees = emsEmployeeRepo.findByEmployerDetailsId(idMask.unmask(employerId));
        return employees.stream()
                .map(EmsEntityDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmsEmployeeDto createEmployeeKafka(EmsEmployeeDto em) {
        EmsEmployeeDto employee = createEmployee(em);
        EmsEmailDto emailData = new EmsEmailDto().builder()
                .to(employee.getUsername())
                .phone(employee.getPhone())
                .subject("Your new Account info")
                .body("Your username is " + employee.getUsername() + " Follow the link to reset password http://localhost:5173/reset-password?id=" + employee.getId())
                .build();
        kafkaTemplate.send("send-employee-email", emailData);
        return employee ;
    }


    @Override
    public boolean generateToken(String empId) throws EmsCustomException {
        EmsUserToken emsUserToken = emsUserTokenService.generateToken(empId);
        if (emsUserToken == null) {
            return false;
        }
        Optional<EmsEmployeeDto> employeeOptional = getEmployeebyId(idMask.mask(emsUserToken.getId()));
        if (employeeOptional.isPresent()) {
            EmsEmployeeDto employee = employeeOptional.get();
            EmsEmailDto emailData = EmsEmailDto.builder()
                    .to(employee.getUsername())
                    .phone(employee.getPhone())
                    .subject("Your Token Code For Password Reset")
                    .body("Your code is " + emsUserToken.getToken() +
                            ". Proceed to password reset through this link http://localhost:5173/reset-password?user=" +
                            employee.getId() + " or continue on your previous page.")
                    .build();
            kafkaTemplate.send("send-employee-email", emailData);
            return true;
        }

        return false;
    }

    @Override
    public boolean verifyTokenAndUpdatePassword(String userId, EmsTokenDto emsTokenDto) throws EmsCustomException {
        try{
            EmsUserToken byTokenAndUserId = emsUserTokenService.getByTokenAndUserId(emsTokenDto.getToken(), userId);
            if(! emsUserTokenService.checkExpiration(byTokenAndUserId)){
                //update password
                updateEmployeePassword(userId, emsTokenDto.getPassword());
                //delete token
                emsUserTokenService.deleteByTokenId(byTokenAndUserId.getId());
                return true;
            }
        }catch (EmsCustomException e){
           throw e;
        }catch(Exception e){
            throw new EmsCustomException("An error occurred while processing", "404");
        }
        return false;
    }

}
