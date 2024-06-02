package com.prajwol.service;

import com.prajwol.dto.EmsScheduleDto;
import com.prajwol.entity.EmsSchedule;
import com.prajwol.exception.EmsCustomException;

import java.util.List;

public interface EmsScheduleService {
    public EmsSchedule addEmsSchedule(EmsScheduleDto emsScheduleDto);

    public EmsSchedule updateEmsSchedule(String scheduleId, EmsScheduleDto emsScheduleDto) throws EmsCustomException;

    public void deleteEmsSchedule(String scheduleId);

    public EmsSchedule getEmsSchedule(String scheduleId) throws EmsCustomException;

    public List<EmsSchedule> getAllSchedules();

    public List<EmsSchedule> getSchedulesByDepartmentId(String departmentId);

    public List<EmsSchedule> getSchedulesByEmployerId(String employerId);

    public List<EmsSchedule> getSchedulesWithEndDateInFuture();

    public List<EmsSchedule> getSchedulesWithEndDateInFuture(String employerId)  throws EmsCustomException ;

}
