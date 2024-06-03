package com.prajwol.controller;

import com.prajwol.dto.EmailDto;
import com.prajwol.service.EmsEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class NotifyController {
    private EmsEmailService emsEmailService;
    @Autowired
    public NotifyController(EmsEmailService emsEmailService) {
        this.emsEmailService = emsEmailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDto emailDto){
        try{
            emsEmailService.sendHtmlEmail(emailDto.getTo(), emailDto.getSubject(),emailDto.getBody());
        }catch (Exception e){
            return "Error sending email";
        }
        return "Email Sent";
    }

}
