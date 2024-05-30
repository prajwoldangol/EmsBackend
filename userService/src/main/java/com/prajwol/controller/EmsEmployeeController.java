package com.prajwol.controller;

import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.service.EmsEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmsEmployeeController {
    private EmsEmployeeService emsEmployeeService;

    @Autowired
    public EmsEmployeeController(EmsEmployeeService emsEmployeeService) {
        this.emsEmployeeService = emsEmployeeService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserAuthResDto> login(@RequestBody UserAuthReqDto userReqDto, HttpServletResponse response) {
        UserAuthResDto userAuthResDto = emsEmployeeService.loginEmployee(userReqDto);
        if(userAuthResDto.getStatusCode() == HttpStatus.OK.value()){
            Cookie refreshTokenCookie = new Cookie("refreshToken", userAuthResDto.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true); // Set HTTPOnly flag
            refreshTokenCookie.setSecure(true); // Set Secure flag for HTTPS
            refreshTokenCookie.setMaxAge(60 * 60 * 4); // Set expiration time (4 hours)
            refreshTokenCookie.setPath("/"); // Set cookie path
            response.addCookie(refreshTokenCookie);
        }
//        userAuthResDto.setRefreshToken(null);
        return ResponseEntity.ok(userAuthResDto);
    }

    @PostMapping("/register")
    public ResponseEntity<EmsEmployee> register(@RequestBody EmsEmployee emsEmployee) {
        return ResponseEntity.ok(emsEmployeeService.registerEmployee(emsEmployee));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Coming form employee testing passed");
    }
}
