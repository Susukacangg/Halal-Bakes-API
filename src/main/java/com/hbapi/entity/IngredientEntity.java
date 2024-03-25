package com.hbapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Column(name = "halal_status")
    private String halalStatus;
}
