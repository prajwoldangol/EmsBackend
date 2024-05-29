package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.exception.EmsCustomException;
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

import java.util.HashMap;
import java.util.Optional;

@Service
@Log4j2
public class EmsEmployerServiceImpl implements EmsEmployerService {

    private EmsEmployerRepo emsEmployerRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;//
    private JwtTokenProvider jwtTokenProvider;
    private IdObfuscationService idObfuscationService;
    @Autowired
    public EmsEmployerServiceImpl(@Qualifier("employerAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployerRepo emsEmployerRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,  IdObfuscationService idObfuscationService) {
        this.emsEmployerRepo = emsEmployerRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.idObfuscationService = idObfuscationService;
    }

    public EmsEmployer registerEmployer(EmsEmployer em) {
        em.setPassword(passwordEncoder.encode(em.getPassword()));
        EmsEmployer emp = emsEmployerRepo.save(em);
        log.info("Employer registered successfully");
        return emp;
    }

    public Optional<EmsEmployer> getEmployerbyId(long id) {
        return emsEmployerRepo.findById(id);
    }

    public void deleteEmployer(Long employerId) {
        emsEmployerRepo.deleteById(employerId);
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
}
