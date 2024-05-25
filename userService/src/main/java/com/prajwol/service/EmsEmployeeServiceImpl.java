package com.prajwol.service;

import com.prajwol.config.JwtTokenProvider;
import com.prajwol.dto.UserReqDto;
import com.prajwol.dto.UserResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsEmployeeRepo;
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
public class EmsEmployeeServiceImpl implements EmsEmployeeService {
    private EmsEmployeeRepo emsEmployeeRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EmsEmployeeServiceImpl(@Qualifier("employeeAuthenticationManager") AuthenticationManager authenticationManager, EmsEmployeeRepo emsEmployeeRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.emsEmployeeRepo = emsEmployeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public UserResDto loginEmployee(UserReqDto userReqDto) {
        UserResDto employeeResDto = new UserResDto();
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
            employeeResDto.setRole(user.getRole().name());
        } catch (Exception e) {
            employeeResDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return employeeResDto;
    }
}
