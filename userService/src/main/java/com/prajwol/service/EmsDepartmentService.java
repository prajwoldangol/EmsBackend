package com.prajwol.service;

import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.exception.EmsCustomException;

import java.util.List;

public interface EmsDepartmentService {

    public EmsDepartmentDto createDepartment(EmsDepartmentDto emsDepartmentDto);
    public void deleteDepartment(String id);
    public List<EmsDepartmentDto> getAllDepartments();
    public EmsDepartmentDto updateDepartment(String id, EmsDepartmentDto emsDepartmentDto) throws EmsCustomException;

    EmsDepartmentDto getDepartmentById(String id) throws EmsCustomException;
}
