package com.jamith.rmi.entity;

import com.jamith.rmi.dto.QuestionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jamith Nimantha
 */
@Entity(name = "question")
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<Answer> answers;

//    @OneToMany(mappedBy = "question")
//    private List<Response> responses;

    public QuestionDTO toDto() {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(this.id);
        dto.setType(this.type);
        dto.setName(this.name);
        dto.setAnswerDTOS(this.answers.stream().map(Answer::toDTO).collect(Collectors.toList()));
        return dto;
    }

}
