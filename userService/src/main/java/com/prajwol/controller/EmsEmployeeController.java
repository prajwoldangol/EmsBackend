package com.prajwol.controller;

import com.prajwol.dto.UserReqDto;
import com.prajwol.dto.UserResDto;
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
    public ResponseEntity<UserResDto> login(@RequestBody UserReqDto userReqDto) {
        return ResponseEntity.ok(emsEmployeeService.loginEmployee(userReqDto));
    }

    @PostMapping("/register")
    public ResponseEntity<EmsEmployee> register(@RequestBody EmsEmployee emsEmployee) {
        return ResponseEntity.ok(emsEmployeeService.registerEmployee(emsEmployee));
    }

    @GetMapping("/test")
    public String test() {
        return "Coming form employee testing passed";
    }
}
