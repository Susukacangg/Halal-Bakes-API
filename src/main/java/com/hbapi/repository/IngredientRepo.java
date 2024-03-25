package com.hbapi.repository;

import com.hbapi.entity.IngredientEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepo extends JpaRepository<IngredientEntity, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM ingredient
            WHERE ingredient_id = :ingredientId
            """)
    Optional<IngredientEntity> findIngredientByIngredientId(@Param("ingredientId") Integer ingredientId);
}
