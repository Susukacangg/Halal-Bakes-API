package com.hbapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_info_ingredient")
public class ProductInfoIngredient {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "barcode_fk")
    private String barcode;

    @Column(name = "ingredient_id_fk")
    private Integer ingredientId;
}
