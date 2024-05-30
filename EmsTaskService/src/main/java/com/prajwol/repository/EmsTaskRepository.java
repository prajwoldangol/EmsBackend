package com.prajwol.repository;

import com.prajwol.Entity.EmsTask;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmsTaskRepository extends MongoRepository<EmsTask, ObjectId> {
}
