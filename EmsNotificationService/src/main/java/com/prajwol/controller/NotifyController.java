package com.prajwol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing")
public class NotifyController {
    @GetMapping("/hello")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test passed" );
    }
    @GetMapping("/hello2")
    public String tests(){
        return "HELLOW from Notify";
    }
}
