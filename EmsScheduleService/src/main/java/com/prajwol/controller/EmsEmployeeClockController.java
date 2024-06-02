package com.prajwol.controller;

import com.prajwol.entity.EmsEmployeeClockData;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsEmployeeClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/clock")
public class EmsEmployeeClockController {
    private final EmsEmployeeClockService employeeClockService;

    @Autowired
    public EmsEmployeeClockController(EmsEmployeeClockService employeeClockService) {
        this.employeeClockService = employeeClockService;
    }

    @PostMapping("/clockin/{employeeId}")
    public ResponseEntity<EmsEmployeeClockData> clockIn(@PathVariable String employeeId, @RequestBody Instant clockInTime) {
        try {
            EmsEmployeeClockData clock = employeeClockService.clockIn(employeeId, clockInTime);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(null);
        }
    }

    @PostMapping("/clockout/{employeeId}")
    public ResponseEntity<EmsEmployeeClockData> clockOut(@PathVariable String employeeId, @RequestBody Instant clockOutTime) {
        try {
            EmsEmployeeClockData clock = employeeClockService.clockOut(employeeId, clockOutTime);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(null);
        }
    }

    @PostMapping("/breakin/{employeeId}")
    public ResponseEntity<EmsEmployeeClockData> breakIn(@PathVariable String employeeId, @RequestBody Instant breakInTime) {
        try {
            EmsEmployeeClockData clock = employeeClockService.breakIn(employeeId, breakInTime);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(null);
        }
    }

    @PostMapping("/breakout/{employeeId}")
    public ResponseEntity<EmsEmployeeClockData> breakOut(@PathVariable String employeeId, @RequestBody Instant breakOutTime) {
        try {
            EmsEmployeeClockData clock = employeeClockService.breakOut(employeeId, breakOutTime);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(null);
        }
    }
    @GetMapping("/{id}/{employeeId}")
    public ResponseEntity<EmsEmployeeClockData> getByIdAndEmployeeId(@PathVariable String id, @PathVariable String employeeId) {
        try {
            EmsEmployeeClockData clock = employeeClockService.getByIdAndEmployeeId(id, employeeId);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
