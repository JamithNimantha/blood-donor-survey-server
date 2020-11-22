package com.jamith.rmi.repository;

import com.jamith.rmi.entity.Question;

/**
 * @author Jamith Nimantha
 */
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    Integer saveQuestion(Question question) throws Exception;
}
