package com.prajwol.repository;

import com.prajwol.entity.EmsUserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmsUserTokenRepo extends JpaRepository<EmsUserToken, Long> {
    Optional<EmsUserToken> findByToken(String token);
    Optional<EmsUserToken> findByTokenAndUserId(String token, String userId);
}