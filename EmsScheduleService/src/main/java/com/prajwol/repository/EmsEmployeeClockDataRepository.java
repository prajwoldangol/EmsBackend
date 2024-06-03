package com.prajwol.repository;

import com.prajwol.entity.EmsEmployeeClockData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EmsEmployeeClockDataRepository extends MongoRepository<EmsEmployeeClockData, ObjectId> {
    List<EmsEmployeeClockData> findByEmployeeIdAndClockOutTimeAfter(String employeeId, Instant clockOutTime);
    List<EmsEmployeeClockData> findByEmployeeIdAndClockInTimeBetween(String employeeId, Instant start, Instant end);

    List<EmsEmployeeClockData> findByEmployeeIdAndClockOutTimeBetween(String employeeId, Instant startOfToday, Instant endOfToday);

    Optional<EmsEmployeeClockData> findByEmployeeIdAndTodayDate(String employeeId, Instant today);

    Optional<EmsEmployeeClockData> findByIdAndEmployeeId(String id, String employeeId);
}
