package com.jamith.rmi.util;

import com.jamith.rmi.dto.AnswerDTO;
import com.jamith.rmi.dto.QuestionDTO;
import com.jamith.rmi.dto.ResponseDTO;
import com.jamith.rmi.dto.UserDTO;
import com.jamith.rmi.entity.Answer;
import com.jamith.rmi.entity.Question;
import com.jamith.rmi.entity.Response;
import com.jamith.rmi.entity.User;

/**
 * @author Jamith Nimantha
 */
public class ToEntity {

    private ToEntity() {
    }

    public static User toUserEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setType(dto.getType());
        user.setPassword(dto.getPassword());
        user.setSalt(dto.getSalt());
        return user;
    }

    public static Question toQuestionEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setName(dto.getName());
        question.setType(dto.getType());
        return question;
    }

    public static Answer toAnswerEntity(AnswerDTO dto) {
        Answer answer = new Answer();
        answer.setId(dto.getId());
        answer.setName(dto.getName());
        if (dto.getQuestionDTO() != null)
            answer.setQuestion(toQuestionEntity(dto.getQuestionDTO()));
        return answer;
    }

    public static Response toResponseEntity(ResponseDTO dto) {
        Response response = new Response();
        response.setId(dto.getId());
        response.setAnswer(toAnswerEntity(dto.getAnswerDTO()));
        response.setUser(toUserEntity(dto.getUserDTO()));
        response.setDate(dto.getDate());
        return response;
    }
}
