package com.prajwol.service;

import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.exception.EmsCustomException;

import java.util.List;

public interface EmsDepartmentService {

    public EmsDepartment createDepartment(EmsDepartmentDto emsDepartmentDto);
    public void deleteDepartment(String id);
    public List<EmsDepartment> getAllDepartments();
    public EmsDepartment updateDepartment(String id, EmsDepartmentDto emsDepartmentDto) throws EmsCustomException;

    EmsDepartment getDepartmentById(String id) throws EmsCustomException;
}
