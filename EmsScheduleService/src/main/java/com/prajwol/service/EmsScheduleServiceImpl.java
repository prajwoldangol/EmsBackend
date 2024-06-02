package com.prajwol.service;

import com.prajwol.dto.EmsEmployeeScheduleDto;
import com.prajwol.dto.EmsScheduleDto;
import com.prajwol.entity.EmsEmployeeSchedule;
import com.prajwol.entity.EmsSchedule;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsScheduleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmsScheduleServiceImpl implements EmsScheduleService{
    private EmsScheduleRepository emsScheduleRepository;

    @Autowired
    public EmsScheduleServiceImpl(EmsScheduleRepository emsScheduleRepository) {
        this.emsScheduleRepository = emsScheduleRepository;
    }


    @Override
    public void deleteEmsSchedule(String scheduleId) {
        ObjectId id = new ObjectId(scheduleId);
        emsScheduleRepository.deleteById(id);
    }

    @Override
    public EmsSchedule addEmsSchedule(EmsScheduleDto emsScheduleDto) {
        EmsSchedule emsSchedule = convertToEntity(emsScheduleDto);
        return emsScheduleRepository.save(emsSchedule);
    }

    @Override
    public EmsSchedule updateEmsSchedule(String scheduleId, EmsScheduleDto emsScheduleDto) throws EmsCustomException {
        ObjectId id = new ObjectId(scheduleId);
        Optional<EmsSchedule> optionalSchedule = emsScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            EmsSchedule existingSchedule = optionalSchedule.get();
            EmsSchedule updatedSchedule = convertToEntity(emsScheduleDto);
            updatedSchedule.setId(existingSchedule.getId());

            return emsScheduleRepository.save(updatedSchedule);
        } else {
            throw new EmsCustomException("Schedule not found", "403");
        }
    }

    @Override
    public EmsSchedule getEmsSchedule(String scheduleId) throws EmsCustomException {
        ObjectId id = new ObjectId(scheduleId);
        return emsScheduleRepository.findById(id)
                .orElseThrow(() -> new EmsCustomException("Schedule not found with id:" + scheduleId, "404" ));
    }

    @Override
    public List<EmsSchedule> getAllSchedules() {
        return emsScheduleRepository.findAll();
    }

    @Override
    public List<EmsSchedule> getSchedulesByDepartmentId(String departmentId) {
        return emsScheduleRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<EmsSchedule> getSchedulesByEmployerId(String employerId) {
        return emsScheduleRepository.findByEmployerId(employerId);
    }

    @Override
    public List<EmsSchedule> getSchedulesWithEndDateInFuture() {
        Instant now = Instant.now();
        return emsScheduleRepository.findByEndDateAfter(now);
    }

//    @Override
//    public List<EmsSchedule> getSchedulesWithEndDateInFuture(String employerId) {
//        Instant now = Instant.now();
//        return emsScheduleRepository.findByEmployerIdAndEndDateAfter(employerId, now);
//    }

    @Override
    public List<EmsSchedule> getSchedulesWithEndDateInFuture(String employerId) throws EmsCustomException {
        Instant now = Instant.now();
        List<EmsSchedule> schedules = emsScheduleRepository.findByEmployerIdAndEndDateAfter(employerId, now);
        if (schedules.isEmpty()) {
            throw new EmsCustomException("No schedules found for employer with id: " + employerId, "404");
        }
        return schedules;
    }
    private EmsSchedule convertToEntity(EmsScheduleDto dto) {
        Map<Instant, List<EmsEmployeeSchedule>> employeeSchedules = new LinkedHashMap<>();
        dto.getEmployeeSchedules().forEach((date, schedules) -> {
            employeeSchedules.put(date, convertEmployeeSchedules(date, schedules));
        });

        return EmsSchedule.builder()
                .id(dto.getId())
                .employerId(dto.getEmployerId())
                .departmentId(dto.getDepartmentId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .employeeSchedules(employeeSchedules)
                .build();
    }

    private List<EmsEmployeeSchedule> convertEmployeeSchedules(Instant date, List<EmsEmployeeScheduleDto> dtoList) {
        return dtoList.stream().map(dto -> EmsEmployeeSchedule.builder()
                .employeeId(dto.getEmployeeId())
                .startTime(combineDateAndTime(date, dto.getStartTime()))
                .breakTime(combineDateAndTime(date, dto.getBreakTime()))
                .endTime(combineDateAndTime(date, dto.getEndTime()))
                .build()).collect(Collectors.toList());
    }

    private Instant combineDateAndTime(Instant date, Instant time) {
        LocalDate localDate = LocalDate.ofInstant(date, ZoneId.systemDefault());
        LocalTime localTime = LocalTime.ofInstant(time, ZoneId.systemDefault());
        return localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant();
    }
}
