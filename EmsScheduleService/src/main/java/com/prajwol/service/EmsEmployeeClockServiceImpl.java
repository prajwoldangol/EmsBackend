package com.prajwol.service;

import com.prajwol.entity.EmsEmployeeClockData;
import com.prajwol.entity.EmsEmployeeSchedule;
import com.prajwol.entity.EmsSchedule;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsEmployeeClockDataRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmsEmployeeClockServiceImpl implements EmsEmployeeClockService {
    private final EmsEmployeeClockDataRepository emsEmployeeClockDataRepository;
    private final EmsScheduleService emsScheduleService;

    @Autowired
    public EmsEmployeeClockServiceImpl(EmsEmployeeClockDataRepository emsEmployeeClockDataRepository, EmsScheduleService emsScheduleService) {
        this.emsEmployeeClockDataRepository = emsEmployeeClockDataRepository;
        this.emsScheduleService = emsScheduleService;
    }
    @Override
    public EmsEmployeeClockData getByIdAndEmployeeId(String employeeClockId, String employeeId) throws EmsCustomException {
        ObjectId id = new ObjectId(employeeClockId);
        return emsEmployeeClockDataRepository.findByIdAndEmployeeId(id, employeeId)
                .orElseThrow(() -> new EmsCustomException("Employee clock record not found", "404"));
    }
    @Override
    public EmsEmployeeClockData clockIn(String employeeId, Instant clockInTime) throws EmsCustomException {
        validateClockInTime(employeeId, clockInTime, "Clock-in");

        EmsEmployeeClockData clock = new EmsEmployeeClockData().builder()
                .clockInTime(clockInTime)
                .employeeId(employeeId)
                .todayDate(Instant.now().truncatedTo(ChronoUnit.DAYS))
                .build();
        return emsEmployeeClockDataRepository.save(clock);
    }
    @Override
    public EmsEmployeeClockData clockOut(String employeeId, Instant clockOutTime) throws EmsCustomException {
        EmsEmployeeClockData clock = getLastActiveClock(employeeId);
        clock.setClockOutTime(clockOutTime);

        return emsEmployeeClockDataRepository.save(clock);
    }
    @Override
    public EmsEmployeeClockData breakIn(String employeeId, Instant breakInTime) throws EmsCustomException {
        validateClockInTime(employeeId, breakInTime, "Break-in");

        EmsEmployeeClockData clock = getLastActiveClock(employeeId);
        clock.setBreakInTime(breakInTime);

        return emsEmployeeClockDataRepository.save(clock);
    }
    @Override
    public EmsEmployeeClockData breakOut(String employeeId, Instant breakOutTime) throws EmsCustomException {
        EmsEmployeeClockData clock = getLastActiveClock(employeeId);
        clock.setBreakOutTime(breakOutTime);

        return emsEmployeeClockDataRepository.save(clock);
    }

    private EmsEmployeeClockData getLastActiveClock(String employeeId) throws EmsCustomException {
        // Get today's date
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);

        // Retrieve today's clock-in record for the specified employee
        EmsEmployeeClockData lastClock = emsEmployeeClockDataRepository.findByEmployeeIdAndTodayDate(employeeId, today);

        // Check if a clock-in record exists for today
        if (lastClock == null || lastClock.getClockOutTime() != null) {
            throw new EmsCustomException("No active clock-in found to clock out from", "403");
        }
        return lastClock;
    }


    private void validateClockInTime(String employeeId, Instant clockInTime, String clockType) throws EmsCustomException {
// Fetch today's schedule
        List<EmsSchedule> schedules = emsScheduleService.getSchedulesWithEndDateInFuture(employeeId); // Update with actual employerId

        // Check if the employee is scheduled for today
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        boolean isScheduledForToday = schedules.stream()
                .anyMatch(schedule -> schedule.getEmployeeSchedules().containsKey(today) &&
                        schedule.getEmployeeSchedules().get(today).stream()
                                .anyMatch(empSchedule -> empSchedule.getEmployeeId().equals(employeeId)));

        if (!isScheduledForToday) {
            throw new EmsCustomException("Employee is not scheduled for today", "403");
        }

        // Fetch the start time for today's schedule
        Instant startTime = schedules.stream()
                .filter(schedule -> schedule.getEmployeeSchedules().containsKey(today))
                .flatMap(schedule -> schedule.getEmployeeSchedules().get(today).stream())
                .filter(empSchedule -> empSchedule.getEmployeeId().equals(employeeId))
                .findFirst()
                .map(EmsEmployeeSchedule::getStartTime)
                .orElse(null);

        if (startTime == null) {
            throw new EmsCustomException("Employee's start time not found for today", "403");
        }

        // Ensure clock-in is within 15 minutes before start time and after start time
        Instant fifteenMinutesBefore = startTime.minus(15, ChronoUnit.MINUTES);
        if (clockInTime.isBefore(fifteenMinutesBefore)) {
            throw new EmsCustomException(clockType + " not allowed more than 15 minutes before start time or after start time", "403");
        }

        // Ensure at least 30 minutes between clock-out and next clock-in
        List<EmsEmployeeClockData> recentClocks = emsEmployeeClockDataRepository.findByEmployeeIdAndClockOutTimeAfter(employeeId, clockInTime.minus(30, ChronoUnit.MINUTES));
        if (!recentClocks.isEmpty()) {
            throw new EmsCustomException("You must wait 30 minutes after clocking out before " + clockType + " again", "403");
        }
    }
}
