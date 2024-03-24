package com.hbapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_info_additive")
public class ProductInfoAdditive {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "barcode_fk")
    private String barcode;

    @Column(name = "ecode_fk")
    private String ecode;
}
