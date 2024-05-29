package com.prajwol.controller;

import com.prajwol.dto.UserAuthReqDto;
import com.prajwol.dto.UserAuthResDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.service.EmsEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<UserAuthResDto> login(@RequestBody UserAuthReqDto userReqDto) {
        return ResponseEntity.ok(emsEmployeeService.loginEmployee(userReqDto));
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
