package com.prajwol.repository;

import com.prajwol.entity.EmsUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmsUserDetailsRepo extends JpaRepository<EmsUserDetails, Long> {
}
