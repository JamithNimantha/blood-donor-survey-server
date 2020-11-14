package com.jamith.rmi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Jamith Nimantha
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "question_id")
    private List<Answer> answers;

    @Column
    @OneToMany(mappedBy = "question_id")
    private List<Question> questions;
}
