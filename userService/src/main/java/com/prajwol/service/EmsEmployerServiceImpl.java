package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.*;
import com.prajwol.dto.dtoservice.EmsEntityDtoConverter;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.entity.EmsUserToken;
import com.prajwol.exception.EmsCustomException;
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

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class EmsEmployerServiceImpl implements EmsEmployerService {

    private final KafkaTemplate<String, EmsEmailDto> kafkaTemplate;
    private final EmsUserTokenService emsUserTokenService;
    private EmsEmployerRepo emsEmployerRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;//
    private JwtTokenProvider jwtTokenProvider;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask;

    @Autowired
    public EmsEmployerServiceImpl(@Qualifier("employerAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployerRepo emsEmployerRepo, PasswordEncoder passwordEncoder, EmsUserTokenService emsUserTokenService, JwtTokenProvider jwtTokenProvider, KafkaTemplate<String, EmsEmailDto> kafkaTemplate, IdObfuscationService idObfuscationService) {
        this.emsEmployerRepo = emsEmployerRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emsUserTokenService = emsUserTokenService;
        this.kafkaTemplate = kafkaTemplate;
        this.idObfuscationService = idObfuscationService;
        this.idMask = idObfuscationService.idMask();
    }

    public EmsEmployerResponseDto registerEmployer(EmsEmployerRequestDto em) {
        EmsEmployer emsEmployer = new EmsEmployer().builder()
                .password(passwordEncoder.encode(em.getPassword()))
                .role(EmsRole.USER)
                .signedUpDate(Instant.now())
                .username(em.getUsername())
                .companyName(em.getCompanyName())
                .phone(em.getPhone())
                .build();

        EmsEmployer emp = emsEmployerRepo.save(emsEmployer);
        log.info("Employer registered successfully");
        return EmsEntityDtoConverter.toEmployerDto(emp);
    }


    public Optional<EmsEmployerResponseDto> getEmployerbyId(String id) {
        return emsEmployerRepo.findById(idMask.unmask(id))
                .map(EmsEntityDtoConverter::toEmployerDto);
    }

    @Override
    public Optional<EmsEmployer> getById(Long id) {
        return emsEmployerRepo.findById(id);
    }

    public void deleteEmployer(String employerId) {
        emsEmployerRepo.deleteById(idMask.unmask(employerId));
    }

    @Override
    public UserAuthResDto loginEmployer(UserAuthReqDto employerReqDto) {
        UserAuthResDto employerResDto = new UserAuthResDto();
        try {
            HashMap<String, String> role = new HashMap<>();
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(employerReqDto.getUsername(),
                            employerReqDto.getPassword()));
            var user = emsEmployerRepo.findByUsername(employerReqDto.getUsername()).orElseThrow(() -> new EmsCustomException("User Not Found", "404"));
            IdMask<Long> idMask = idObfuscationService.idMask();
            role.put("role", user.getRole().name());
            role.put("username", user.getUsername());
            var jwt = jwtTokenProvider.generateToken(user.getUsername(), role);
            var refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername(), role);
            employerResDto.setStatusCode(HttpStatus.OK.value());
            employerResDto.setToken(jwt);
            employerResDto.setRefreshToken(refreshToken);
            employerResDto.setEmployerId(idMask.mask(user.getId()));
            employerResDto.setUsername(user.getUsername());
            employerResDto.setRole(user.getRole().name());
        } catch (Exception e) {
            employerResDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return employerResDto;
    }

    @Override
    public EmsEmployerResponseDto updateEmployerPassword(String empId, String newPassword) throws EmsCustomException {
        EmsEmployer e = emsEmployerRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employer with id " + empId + " not found.", "404"));

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        e.setPassword(encodedPassword);

        EmsEmployer savedEmployer = emsEmployerRepo.save(e);
        log.info("Password for employee with id " + empId + " updated successfully");
        return EmsEntityDtoConverter.toEmployerDto(savedEmployer);
    }

    @Override
    public EmsEmployerResponseDto updateEmployer(String empId, EmsEmployerRequestDto em) throws EmsCustomException {
        EmsEmployer e = emsEmployerRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + empId + " not found.", "404"));
        log.info(e);
        FieldUtils.updateFieldIfPresent(em.getUsername(), e::setUsername, FieldUtils.NOT_EMPTY_STRING);
        String encodedPassword = em.getPassword() != null ? passwordEncoder.encode(em.getPassword()) : e.getPassword();
        FieldUtils.updateFieldIfPresent(encodedPassword, e::setPassword, Objects::nonNull);
        FieldUtils.updateFieldIfPresent(em.getPhone(), e::setPhone, FieldUtils.NOT_EMPTY_STRING);
        if (em.getRole() != null) {
            EmsRole role = EmsRole.valueOf(em.getRole()); // Convert string to enum
            e.setRole(role);
        }
//        FieldUtils.updateFieldIfPresent(EmsRole.valueOf(em.getRole()), e::setRole, Objects::nonNull);
        if (em.getUserDetailDto() != null) {
            e.setEmsUserDetails(EmsEntityDtoConverter.emsUserDetailsDtoToEntity(em.getUserDetailDto()));
        }
        EmsEmployer savedEmployer = emsEmployerRepo.save(e);
        return EmsEntityDtoConverter.toEmployerDto(savedEmployer);
    }

    @Override
    public EmsEmployerResponseDto updateEmployeeRole(String empId, EmsRole role) throws EmsCustomException {
        EmsEmployer e = emsEmployerRepo.findById(idMask.unmask(empId))
                .orElseThrow(() -> new EmsCustomException("Employee with id " + empId + " not found.", "404"));

        FieldUtils.updateFieldIfPresent(role, e::setRole, Objects::nonNull); // Update role

        return  EmsEntityDtoConverter.toEmployerDto(emsEmployerRepo.save(e)); // Save and return the updated employer
    }

    @Override
    public boolean generateToken(String empId) {
        EmsUserToken emsUserToken = emsUserTokenService.generateToken(empId);
        if (emsUserToken == null) {
            return false;
        }
        Optional<EmsEmployer> employeeOptional = emsEmployerRepo.findById(idMask.unmask(emsUserToken.getUserId()));
        if (employeeOptional.isPresent()) {
            EmsEmployer employer = employeeOptional.get();
            EmsEmailDto emailData = EmsEmailDto.builder()
                    .to(employer.getUsername())
                    .phone(employer.getPhone())
                    .subject("Your Token Code For Password Reset")
                    .body("Your code is " + emsUserToken.getToken() +
                            ". Proceed to password reset through this link http://localhost:5173/reset-password?user=" +
                            idMask.mask(employer.getId()) + " or continue on your previous page.")
                    .build();
            kafkaTemplate.send("send-employee-email", emailData);
            return true;
        }

        return false;
    }

    @Override
    public boolean verifyTokenAndUpdatePassword(String userId, EmsTokenDto emsTokenDto) throws EmsCustomException {
        try {
            EmsUserToken byTokenAndUserId = emsUserTokenService.getByTokenAndUserId(emsTokenDto.getToken(), userId);
            if (!emsUserTokenService.checkExpiration(byTokenAndUserId)) {
                //update password
                updateEmployerPassword(userId, emsTokenDto.getPassword());
                //delete token
                emsUserTokenService.deleteByTokenId(byTokenAndUserId.getId());
                return true;
            }
        } catch (EmsCustomException e) {
            throw e;
        } catch (Exception e) {
            throw new EmsCustomException("An error occurred while processing", "404");
        }
        return false;
    }

    @Override
    public EmsEmployer getByUsername(String username) throws EmsCustomException {
        return emsEmployerRepo.findByUsername(username)
                .orElseThrow(() -> new EmsCustomException("Not Found", "404"));
    }

    @Override
    public EmsEmployer saveEmployer(EmsEmployer em) {
        return emsEmployerRepo.save(em);
    }
}
