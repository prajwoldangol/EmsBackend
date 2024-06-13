package com.prajwol.repository;

import com.prajwol.entity.EmsEmployer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmsEmployerRepo extends JpaRepository<EmsEmployer, Long> {

    Optional<EmsEmployer> findByUsername(String username);
    @Query("SELECT e FROM EmsEmployer e JOIN FETCH e.emsEmployee emp WHERE e.id = :employerId")
    Optional<EmsEmployer> findEmployerWithEmployeesById(@Param("employerId") Long employerId);

    @Query("SELECT e FROM EmsEmployer e LEFT JOIN FETCH e.emsUserDetails WHERE e.username = :username")
    Optional<EmsEmployer> findEmployerWithUserDetailsByUsername(@Param("username") String username);

    @Query("SELECT e FROM EmsEmployer e " +
            "LEFT JOIN FETCH e.emsEmployee emp " +
            "LEFT JOIN FETCH e.departments dept " +
            "WHERE e.id = :employerId")
    Optional<EmsEmployer> findEmployerWithEmployeesAndDepartmentsById(@Param("employerId") Long employerId);
}
