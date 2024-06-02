package com.prajwol.controller;

import com.prajwol.dto.EmsScheduleDto;
import com.prajwol.entity.EmsSchedule;
import com.prajwol.exception.EmsCustomErrorResponse;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsScheduleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
@Log4j2
public class EmsScheduleController {

    private final EmsScheduleService emsScheduleService;

    @Autowired
    public EmsScheduleController(EmsScheduleService emsScheduleService) {
        this.emsScheduleService = emsScheduleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmsSchedule(@PathVariable String id) {
        try{
            return ResponseEntity.ok(emsScheduleService.getEmsSchedule(id));
        }catch (EmsCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage()));
        }

    }

    @PostMapping
    public ResponseEntity<EmsSchedule> addEmsSchedule(@RequestBody EmsScheduleDto emsScheduleDto) {
        return ResponseEntity.ok(emsScheduleService.addEmsSchedule(emsScheduleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmsSchedule(@PathVariable String id, @RequestBody EmsScheduleDto emsScheduleDto)  {
        try{
            return ResponseEntity.ok(emsScheduleService.updateEmsSchedule(id, emsScheduleDto));
        }catch (EmsCustomException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmsSchedule(@PathVariable String id) {
        emsScheduleService.deleteEmsSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmsSchedule>> getAllSchedules() {
        return ResponseEntity.ok(emsScheduleService.getAllSchedules());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmsSchedule>> getSchedulesByDepartmentId(@PathVariable String departmentId) {
        return ResponseEntity.ok(emsScheduleService.getSchedulesByDepartmentId(departmentId));
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<EmsSchedule>> getSchedulesByEmployerId(@PathVariable String employerId) {
        return ResponseEntity.ok(emsScheduleService.getSchedulesByEmployerId(employerId));
    }

    @GetMapping("/future")
    public ResponseEntity<List<EmsSchedule>> getSchedulesWithEndDateInFuture() {
        return ResponseEntity.ok(emsScheduleService.getSchedulesWithEndDateInFuture());
    }
    @GetMapping("/future/{employerId}")
    public ResponseEntity<?> getSchedulesWithEndDateInFuture(@PathVariable String employerId) {
        try {
            List<EmsSchedule> schedules = emsScheduleService.getSchedulesWithEndDateInFuture(employerId);
            return ResponseEntity.ok(schedules);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
