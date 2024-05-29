package com.prajwol.repository;

import com.prajwol.entity.EmsEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmsEmployeeRepo extends JpaRepository<EmsEmployee, Long> {
    Optional<EmsEmployee> findByUsername(String username);
    List<EmsEmployee> findByEmployerDetailsId(Long employerId);
}
