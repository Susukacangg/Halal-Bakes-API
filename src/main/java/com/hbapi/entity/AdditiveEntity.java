package com.hbapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "additive")
public class AdditiveEntity {
    @Id
    @Column(name = "ecode")
    private String ecodeId;

    @Column(name = "halal_status")
    private String halalStatus;

    @Column(name = "description")
    private String description;
}
