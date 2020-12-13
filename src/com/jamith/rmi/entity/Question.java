package com.jamith.rmi.entity;

import com.jamith.rmi.dto.QuestionDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jamith Nimantha
 */
@Entity(name = "question")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", answers=").append(answers);
        sb.append('}');
        return sb.toString();
    }
}
