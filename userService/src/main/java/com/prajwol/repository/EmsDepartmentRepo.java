package com.prajwol.repository;

import com.prajwol.entity.EmsDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmsDepartmentRepo extends JpaRepository<EmsDepartment, Long> {
}
