package com.hbapi.repository;

import com.hbapi.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionEntity, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM question
            """)
    List<QuestionEntity> findAllQuestion();
}
