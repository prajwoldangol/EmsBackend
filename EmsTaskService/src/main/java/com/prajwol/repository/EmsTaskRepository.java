package com.prajwol.repository;

import com.prajwol.Entity.EmsTask;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmsTaskRepository extends MongoRepository<EmsTask, ObjectId> {
    List<EmsTask> findByTaskOwner(String taskOwner);
    List<EmsTask> findByDepartment(String department);
}
