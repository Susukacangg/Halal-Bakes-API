package com.hbapi.repository;

import com.hbapi.entity.AnswerEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<AnswerEntity, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM answer
            WHERE question_id_fk = :qId
            """)
    List<AnswerEntity> findAllAnswerByQuestionIdFk(@Param("qId") Integer qId);
}
