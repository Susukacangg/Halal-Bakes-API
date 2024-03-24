package com.hbapi.repository;

import com.hbapi.entity.Ingredient;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM ingredient
            WHERE ingredient_id = :ingredientId
            """)
    Optional<Ingredient> findIngredientByIngredientId(@Param("ingredientId") Integer ingredientId);
}
