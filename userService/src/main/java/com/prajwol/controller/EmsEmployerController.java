package com.prajwol.controller;

import com.prajwol.dto.UserReqDto;
import com.prajwol.dto.UserResDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.service.EmsEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employer")
public class EmsEmployerController {
    private EmsEmployerService emsEmployerService;

    @Autowired
    public EmsEmployerController(EmsEmployerService emsEmployerService) {
        this.emsEmployerService = emsEmployerService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserResDto> login(@RequestBody UserReqDto employerReqDto) {
        return ResponseEntity.ok(emsEmployerService.loginEmployer(employerReqDto));
    }

    @PostMapping("/register")
    public ResponseEntity<EmsEmployer> register(@RequestBody EmsEmployer emsEmployer) {
        return ResponseEntity.ok(emsEmployerService.registerEmployer(emsEmployer));
    }
    @GetMapping("/test")
    public String test() {
        return "Api testing passed";
    }
}

