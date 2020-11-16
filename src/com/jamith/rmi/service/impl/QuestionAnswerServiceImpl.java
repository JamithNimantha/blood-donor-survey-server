package com.jamith.rmi.service.impl;

import com.jamith.rmi.dto.QuestionDTO;
import com.jamith.rmi.dto.ResponseDTO;
import com.jamith.rmi.entity.Question;
import com.jamith.rmi.entity.Response;
import com.jamith.rmi.repository.AnswerRepository;
import com.jamith.rmi.repository.QuestionRepository;
import com.jamith.rmi.repository.RepositoryFactory;
import com.jamith.rmi.repository.ResponseRepository;
import com.jamith.rmi.service.QuestionAnswerService;
import com.jamith.rmi.util.ToEntity;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jamith Nimantha
 */

public class QuestionAnswerServiceImpl extends UnicastRemoteObject implements QuestionAnswerService {

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private ResponseRepository responseRepository;

    QuestionAnswerServiceImpl() throws RemoteException {
        questionRepository = RepositoryFactory.getInstance().RepoFactoryFor(RepositoryFactory.RepositoryTypes.QUESTION);
        answerRepository = RepositoryFactory.getInstance().RepoFactoryFor(RepositoryFactory.RepositoryTypes.ANSWER);
        responseRepository = RepositoryFactory.getInstance().RepoFactoryFor(RepositoryFactory.RepositoryTypes.RESPONSE);
    }


    /**
     * Create new Question
     *
     * @param questionDTO QuestionDTO
     * @return true if question saved
     */
    @Override
    public boolean saveQuestion(QuestionDTO questionDTO) {
        Question question = ToEntity.toQuestionEntity(questionDTO);
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the question
     *
     * @param questionDTO QuestionDTO
     * @return true if instance of Question updated
     */
    @Override
    public boolean updateQuestion(QuestionDTO questionDTO) {
        Question question = ToEntity.toQuestionEntity(questionDTO);
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete the Question
     *
     * @param id id of the Question
     * @return true if question deleted
     */
    @Override
    public boolean deleteQuestion(Integer id) throws Exception {
        return questionRepository.delete(id);
    }

    /**
     * Get all the Questions
     *
     * @return all the question
     */
    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        try {
            List<Question> questionList = questionRepository.getAll();
            System.err.println(questionList);
            for (Question question : questionList) {
                questionDTOS.add(question.toDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(questionDTOS);
        return questionDTOS;
    }

    /**
     * Save all the responses
     *
     * @param responseDTOS
     * @return true if Responses saved
     * @throws RemoteException
     */
    @Override
    public boolean saveResponse(List<ResponseDTO> responseDTOS) throws RemoteException {
        String email = responseDTOS.get(0).getUserDTO().getEmail();
        checkIfUserHasPreviousResponse(email);
        for (ResponseDTO responseDTO : responseDTOS) {
            Response response = ToEntity.toResponseEntity(responseDTO);
            response.setDate(new Date());
            try {
                responseRepository.save(response);
            } catch (Exception e) {
                System.err.println("Error Occurred while saving Response ");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void checkIfUserHasPreviousResponse(String email) {
        try {
            List<Response> responseList = responseRepository.findAllResponsesByEmail(email);
            if (!responseList.isEmpty()) {
                for (Response response : responseList) {
                    responseRepository.delete(response.getId());
                    System.out.println("Response Deleted : " + response.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
