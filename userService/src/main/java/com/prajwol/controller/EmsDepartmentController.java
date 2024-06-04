package com.prajwol.controller;

import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.exception.EmsCustomErrorResponse;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer/departments")
public class EmsDepartmentController {
    private final EmsDepartmentService emsDepartmentService;

    @Autowired
    public EmsDepartmentController(EmsDepartmentService emsDepartmentService) {
        this.emsDepartmentService = emsDepartmentService;
    }

    @GetMapping
    public ResponseEntity<List<EmsDepartment>> getAllDepartments() {
        List<EmsDepartment> departments = emsDepartmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable String id) {
        try {
            EmsDepartment department = emsDepartmentService.getDepartmentById(id);
            return ResponseEntity.ok(department);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<EmsDepartment> createDepartment(@RequestBody EmsDepartmentDto emsDepartmentDto) {
        EmsDepartment createdDepartment = emsDepartmentService.createDepartment(emsDepartmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable String id, @RequestBody EmsDepartmentDto emsDepartmentDto) {
        try {
            EmsDepartment updatedDepartment = emsDepartmentService.updateDepartment(id, emsDepartmentDto);
            return ResponseEntity.ok(updatedDepartment);
        } catch (EmsCustomException e) {
            EmsCustomErrorResponse errorResponse = new EmsCustomErrorResponse(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable String id) {
        emsDepartmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();

    }
}
