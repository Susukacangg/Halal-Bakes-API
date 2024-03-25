package com.hbapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @Column(name = "answer_id")
    private Integer answerId;

    @Column(name = "answer")
    private String answer;

    @Column(name = "question_id_fk")
    private Integer questionId;

    @Column(name = "answerer_name")
    private String answererName;
}
