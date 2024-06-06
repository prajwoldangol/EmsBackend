package com.prajwol.controller;

import com.prajwol.dto.*;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomErrorResponse;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsEmployeeService;
import com.prajwol.service.EmsEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employer")
public class EmsEmployerController {
    private final EmsEmployerService emsEmployerService;
    private final EmsEmployeeService emsEmployeeService;
    @Autowired
    public EmsEmployerController(EmsEmployerService emsEmployerService, EmsEmployeeService emsEmployeeService) {
        this.emsEmployerService = emsEmployerService;
        this.emsEmployeeService = emsEmployeeService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserAuthResDto> login(@RequestBody UserAuthReqDto employerReqDto) {
        return ResponseEntity.ok(emsEmployerService.loginEmployer(employerReqDto));
    }

    @PostMapping("/register")
    public ResponseEntity<EmsEmployerResponseDto> register(@RequestBody EmsEmployerRequestDto emsEmployer) {
        return ResponseEntity.ok(emsEmployerService.registerEmployer(emsEmployer));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmsEmployerResponseDto> getEmployerById(@PathVariable String id) {
        Optional<EmsEmployerResponseDto> employerDtoOptional = emsEmployerService.getEmployerbyId(id);
        return employerDtoOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{empId}/password")
    public ResponseEntity<?> updateEmployeePassword(@PathVariable("empId") String empId, @RequestBody String newPassword) {
        try {
            EmsEmployerResponseDto updatedEmployer = emsEmployerService.updateEmployerPassword(empId, newPassword);
            return ResponseEntity.ok(updatedEmployer);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }
    }

    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployer(@PathVariable("empId") String empId, @RequestBody EmsEmployerRequestDto employer) {
        try {
            EmsEmployerResponseDto updatedEmployer = emsEmployerService.updateEmployer(empId, employer);
            return ResponseEntity.ok(updatedEmployer);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/role/{empId}")
    public ResponseEntity<?> updateEmployerRole(@PathVariable("empId") String empId, @RequestBody EmsRole role){
        try {
            EmsEmployerResponseDto updatedEmployer = emsEmployerService.updateEmployeeRole(empId, role);
            return ResponseEntity.ok(updatedEmployer);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("empId") String employerId) {
        emsEmployeeService.deleteEmployee(employerId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable("empId") String employerId) {
        emsEmployerService.deleteEmployer(employerId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/newEmployee")
    public ResponseEntity<EmsEmployeeDto> addNewEmployee(@RequestBody EmsEmployeeDto emsEmployee) {
        return ResponseEntity.ok( emsEmployeeService.createEmployeeKafka(emsEmployee));
    }
    @PostMapping("/generateToken/{empId}")
    public ResponseEntity<String> generateToken(@PathVariable String empId) {
        return emsEmployerService.generateToken(empId) ?
                ResponseEntity.ok("Token generated successfully") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token");

    }

    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<Object> verifyTokenAndUpdatePassword(@PathVariable String userId, @RequestBody EmsTokenDto emsTokenDto) {
        try {
            boolean isPasswordUpdated = emsEmployerService.verifyTokenAndUpdatePassword(userId, emsTokenDto);
            return isPasswordUpdated ?
                    ResponseEntity.ok("Password updated successfully") :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is expired or invalid");
        } catch (EmsCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage()));
        }
    }

}

