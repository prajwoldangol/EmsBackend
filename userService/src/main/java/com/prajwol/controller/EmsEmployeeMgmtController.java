package com.prajwol.controller;

import com.prajwol.dto.EmsEmployeeDto;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.service.EmsEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employer/manage")
public class EmsEmployeeMgmtController {
    private EmsEmployeeService emsEmployeeService;

    @Autowired
    public EmsEmployeeMgmtController(EmsEmployeeService emsEmployeeService) {
        this.emsEmployeeService = emsEmployeeService;
    }
    @PostMapping("/add")
    public void addNewEmployee(@RequestBody EmsEmployeeDto emsEmployee) {
       emsEmployeeService.createEmployeeKafka(emsEmployee);
    }
}
