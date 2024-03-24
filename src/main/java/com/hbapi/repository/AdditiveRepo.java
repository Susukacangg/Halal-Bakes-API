package com.hbapi.repository;

import com.hbapi.entity.Additive;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdditiveRepo extends JpaRepository<Additive, String> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM additive
            WHERE ecode = :ecode
            """)
    Optional<Additive> findEcodeByEcodeId(@Param("ecode") String ecode);
}
