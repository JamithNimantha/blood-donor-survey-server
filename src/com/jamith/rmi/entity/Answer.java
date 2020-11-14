package com.jamith.rmi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jamith Nimantha
 */

@Getter
@Setter
@ToString
@Entity
@Table
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
