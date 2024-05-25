package com.prajwol.repository;

import com.prajwol.entity.EmsEmployer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmsEmployerRepo extends JpaRepository<EmsEmployer, Long> {

    Optional<EmsEmployer> findByUsername(String username);
}
