package com.hbapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "question")
public class QuestionEntity {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "question")
    private String question;

    @Column(name = "num_views")
    private Integer numViews;

    @Column(name = "num_likes")
    private Integer numLikes;
}
