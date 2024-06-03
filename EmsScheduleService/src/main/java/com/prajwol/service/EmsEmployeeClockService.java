package com.prajwol.service;

import com.prajwol.entity.EmsEmployeeClockData;
import com.prajwol.exception.EmsCustomException;

import java.time.Instant;

public interface EmsEmployeeClockService {
    public EmsEmployeeClockData clockIn(String employeeId, String employerId, Instant clockInTime) throws EmsCustomException ;
    public EmsEmployeeClockData clockOut(String employeeId, Instant clockOutTime) throws EmsCustomException;
    public EmsEmployeeClockData breakIn(String employeeId, String employerId,Instant breakInTime) throws EmsCustomException;
    public EmsEmployeeClockData breakOut(String employeeId, Instant breakOutTime) throws EmsCustomException;
    public EmsEmployeeClockData getByIdAndEmployeeId(String id, String employeeId) throws EmsCustomException ;
}
