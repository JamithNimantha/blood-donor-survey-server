package com.jamith.rmi.repository;

import com.jamith.rmi.entity.Answer;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

    /**
     * Get All Answers mapped to the QuestionID
     *
     * @param id question id
     * @return list of Answer Entities
     */
    List<Answer> findAllByQuestionId(Integer id);
}
