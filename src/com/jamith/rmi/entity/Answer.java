package com.jamith.rmi.entity;

import com.jamith.rmi.dto.AnswerDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jamith Nimantha
 */

@Entity(name = "answer")
public class Answer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    /**
     * Convert and Return Answer Entity
     * @return AnswerDTO
     */
    public AnswerDTO toDTO() {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }

    /**
     * @return Generated TOString for the Object
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Answer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
