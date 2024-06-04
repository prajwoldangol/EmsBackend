package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.dto.EmsDepartmentDto;
import com.prajwol.entity.EmsDepartment;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsDepartmentRepo;
import com.prajwol.repository.EmsEmployerRepo;
import com.prajwol.userservice.IdObfuscationService;
import com.prajwol.utility.FieldUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmsDepartmentServiceImpl implements EmsDepartmentService {
    private EmsDepartmentRepo emsDepartmentRepo;
    private EmsEmployerRepo emsEmployerRepo;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask ;

    @Autowired
    public EmsDepartmentServiceImpl(EmsDepartmentRepo emsDepartmentRepo, EmsEmployerRepo emsEmployerRepo, IdObfuscationService idObfuscationService) {
        this.emsDepartmentRepo = emsDepartmentRepo;
        this.emsEmployerRepo = emsEmployerRepo;
        this.idObfuscationService = idObfuscationService;
        this.idMask = idObfuscationService.idMask();
    }

    @Override
    public EmsDepartment createDepartment(EmsDepartmentDto emsDepartmentDto) {
        EmsDepartment emsDepartment = new EmsDepartment();
//        EmsDepartment emsDepartment = new EmsDepartment().builder()
//                .name(emsDepartmentDto.getName())
//                .build();
//        if(emsDepartmentDto.getEmployerId() != null && !emsDepartmentDto.getEmployerId().isEmpty()){
//            Long employerId = idMask.unmask(emsDepartmentDto.getEmployerId());
//            Optional<EmsEmployer> emsEmployer = emsEmployerRepo.findById(employerId);
//            emsEmployer.ifPresent(emsDepartment::setEmsEmployer);
//        }
        setCommonFields(emsDepartment, emsDepartmentDto);
        EmsDepartment savedDepartment = emsDepartmentRepo.save(emsDepartment);
        log.info("A new Department is added for {}", emsDepartmentDto.getEmployerId());
        return savedDepartment ;
    }

    @Override
    public void deleteDepartment(String id) {
        emsDepartmentRepo.deleteById(idMask.unmask(id));
    }

    @Override
    public List<EmsDepartment> getAllDepartments() {
        return emsDepartmentRepo.findAll();
    }

    @Override
    public EmsDepartment updateDepartment(String id, EmsDepartmentDto emsDepartmentDto) throws EmsCustomException {
        Long departmentId = idMask.unmask(id);
        EmsDepartment existingDepartment = emsDepartmentRepo.findById(departmentId)
                .orElseThrow(() -> new EmsCustomException("Department not found", "404"));

//        FieldUtils.updateFieldIfPresent(emsDepartmentDto.getName(), existingDepartment::setName, FieldUtils.NOT_EMPTY_STRING);
//
//        if (emsDepartmentDto.getEmployerId() != null && !emsDepartmentDto.getEmployerId().isEmpty()) {
//            Long employerId = idMask.unmask(emsDepartmentDto.getEmployerId());
//            Optional<EmsEmployer> emsEmployer = emsEmployerRepo.findById(employerId);
//            emsEmployer.ifPresent(existingDepartment::setEmsEmployer);
//        }
        setCommonFields(existingDepartment, emsDepartmentDto);
        EmsDepartment updatedDepartment = emsDepartmentRepo.save(existingDepartment);
        log.info("Department with id {} has been updated", id);
        return updatedDepartment;
    }

    @Override
    public EmsDepartment getDepartmentById(String id) throws EmsCustomException {
        Optional<EmsDepartment> byId = emsDepartmentRepo.findById(idMask.unmask(id));
        return byId.orElseThrow(() -> new EmsCustomException("Department not found", "404"));
    }

    private void setCommonFields(EmsDepartment department, EmsDepartmentDto dto) {
        FieldUtils.updateFieldIfPresent(dto.getName(), department::setName, FieldUtils.NOT_EMPTY_STRING);

        if (dto.getEmployerId() != null && !dto.getEmployerId().isEmpty()) {
            Long employerId = idMask.unmask(dto.getEmployerId());
            Optional<EmsEmployer> emsEmployer = emsEmployerRepo.findById(employerId);
            emsEmployer.ifPresent(department::setEmsEmployer);
        }
    }
}
