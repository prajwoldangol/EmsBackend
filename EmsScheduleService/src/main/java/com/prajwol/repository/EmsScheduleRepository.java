package com.prajwol.repository;

import com.prajwol.entity.EmsSchedule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EmsScheduleRepository extends MongoRepository<EmsSchedule, ObjectId> {
    List<EmsSchedule> findByDepartmentId(String departmentId);

    List<EmsSchedule> findByEmployerId(String employerId);

    List<EmsSchedule> findByEndDateAfter(Instant endDate);

    List<EmsSchedule> findByEmployerIdAndEndDateAfter(String employerId, Instant endDate);
}
