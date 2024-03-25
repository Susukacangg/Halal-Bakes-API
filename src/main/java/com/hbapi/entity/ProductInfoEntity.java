package com.hbapi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_info")
public class ProductInfoEntity {
    @Id
    @Column(name = "barcode")
    private String barcode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "manu_loc")
    private String manuLoc;

    @Column(name = "name")
    private String name;

    @Column(name = "image_link")
    private String imageLink;
}
