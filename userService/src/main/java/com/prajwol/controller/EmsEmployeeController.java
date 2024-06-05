package com.prajwol.controller;

import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.dto.EmsTokenDto;
import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.exception.EmsCustomErrorResponse;
import com.prajwol.exception.EmsCustomException;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody EmsEmployeeDto em) {
        try{
            EmsEmployee updatedEmployee = emsEmployeeService.updateEmployee(id, em);
            return ResponseEntity.ok(updatedEmployee);
        }catch (EmsCustomException e){
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }
    @PutMapping("/{empId}/password")
    public ResponseEntity<?> updateEmployeePassword(@PathVariable String empId, @RequestBody String newPassword) {
        try {
            EmsEmployee updatedEmployee = emsEmployeeService.updateEmployeePassword(empId, newPassword);
            return ResponseEntity.ok(updatedEmployee);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @PostMapping("/generateToken/{empId}")
    public ResponseEntity<String> generateToken(@PathVariable String empId) {
        return emsEmployeeService.generateToken(empId) ?
                ResponseEntity.ok("Token generated successfully") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token");

    }

    @PostMapping("/updatePassword/{userId}")
    public ResponseEntity<Object> verifyTokenAndUpdatePassword(@PathVariable String userId, @RequestBody EmsTokenDto emsTokenDto) {
        try {
            boolean isPasswordUpdated = emsEmployeeService.verifyTokenAndUpdatePassword(userId, emsTokenDto);
            return isPasswordUpdated ?
                    ResponseEntity.ok("Password updated successfully") :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is expired or invalid");
        } catch (EmsCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage()));
        }
    }
}
