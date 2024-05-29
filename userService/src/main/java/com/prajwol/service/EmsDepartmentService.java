package com.prajwol.service;

import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.entity.EmsDepartment;

import java.util.List;

public interface EmsDepartmentService {

    public EmsDepartment createDepartment(EmsDepartmentDto emsDepartmentDto);
    public void deleteDepartment(String id);
    public List<EmsDepartment> getAllDepartments();
}
