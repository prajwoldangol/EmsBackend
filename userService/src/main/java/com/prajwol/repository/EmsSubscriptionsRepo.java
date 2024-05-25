package com.prajwol.repository;

import com.prajwol.entity.EmsSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmsSubscriptionsRepo extends JpaRepository<EmsSubscriptions, Long> {
}
