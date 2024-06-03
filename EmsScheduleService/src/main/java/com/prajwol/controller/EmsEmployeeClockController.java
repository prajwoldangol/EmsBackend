package com.prajwol.controller;

import com.prajwol.dto.EmsEmployeeClockDto;
import com.prajwol.dto.EmsInstantDto;
import com.prajwol.entity.EmsEmployeeClockData;
import com.prajwol.exception.EmsCustomErrorResponse;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsEmployeeClockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/clock")
@Log4j2
public class EmsEmployeeClockController {
    private final EmsEmployeeClockService employeeClockService;

    @Autowired
    public EmsEmployeeClockController(EmsEmployeeClockService employeeClockService) {
        this.employeeClockService = employeeClockService;
    }

    @PostMapping("/clockin/{employeeId}")
    public ResponseEntity<?> clockIn(@PathVariable String employeeId, @RequestBody EmsEmployeeClockDto emsEmployeeClockDto) {
        try {
            log.info(emsEmployeeClockDto);
            EmsEmployeeClockData clock = employeeClockService.clockIn(employeeId,emsEmployeeClockDto.getEmployerId(), emsEmployeeClockDto.getClockInTime());
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/clockout/{employeeId}")
    public ResponseEntity<?> clockOut(@PathVariable String employeeId, @RequestBody EmsInstantDto emsInstantDto) {
        try {
            log.info(emsInstantDto.getTime());
            EmsEmployeeClockData clock = employeeClockService.clockOut(employeeId, emsInstantDto.getTime() );
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/breakin/{employeeId}")
    public ResponseEntity<?> breakIn(@PathVariable String employeeId, @RequestBody EmsEmployeeClockDto emsEmployeeClockDto) {
        try {
            EmsEmployeeClockData clock = employeeClockService.breakIn(employeeId, emsEmployeeClockDto.getEmployerId(), emsEmployeeClockDto.getClockInTime());
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/breakout/{employeeId}")
    public ResponseEntity<?> breakOut(@PathVariable String employeeId,  @RequestBody EmsInstantDto emsInstantDto) {
        try {
            EmsEmployeeClockData clock = employeeClockService.breakOut(employeeId, emsInstantDto.getTime());
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @GetMapping("/{id}/{employeeId}")
    public ResponseEntity<?> getByIdAndEmployeeId(@PathVariable String id, @PathVariable String employeeId) {
        try {
            EmsEmployeeClockData clock = employeeClockService.getByIdAndEmployeeId(id, employeeId);
            return ResponseEntity.ok(clock);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
