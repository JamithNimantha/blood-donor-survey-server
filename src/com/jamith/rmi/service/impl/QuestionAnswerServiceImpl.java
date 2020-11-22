package com.jamith.rmi.service.impl;

import com.jamith.rmi.dto.AnswerDTO;
import com.jamith.rmi.dto.QuestionDTO;
import com.jamith.rmi.dto.ReportDTO;
import com.jamith.rmi.dto.ResponseDTO;
import com.jamith.rmi.entity.Answer;
import com.jamith.rmi.entity.Question;
import com.jamith.rmi.entity.Response;
import com.jamith.rmi.repository.AnswerRepository;
import com.jamith.rmi.repository.QuestionRepository;
import com.jamith.rmi.repository.RepositoryFactory;
import com.jamith.rmi.repository.ResponseRepository;
import com.jamith.rmi.service.QuestionAnswerService;
import com.jamith.rmi.util.ToEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jamith Nimantha
 */

public class QuestionAnswerServiceImpl extends UnicastRemoteObject implements QuestionAnswerService {

    private final transient QuestionRepository questionRepository;

    private final transient AnswerRepository answerRepository;

    private final transient ResponseRepository responseRepository;

    public QuestionAnswerServiceImpl() throws RemoteException {
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
//            List<Answer> answerList = answerRepository.findAllByQuestionId(question.getId());
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answerRepository.update(answer);
            }
            return questionRepository.update(question);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    /**
     * Generate Report with QuickChart.io API
     *
     * @param questionDTO Question DTO
     * @return Byte Array
     * @throws RemoteException
     */
    @Override
    public byte[] generateReport(QuestionDTO questionDTO) throws RemoteException {

        String pieChartUrl = "{type:'doughnut',data:{labels:%label%,datasets:[{data:%data%}]},options:{plugins:" +
                "{datalabels:{display:true,backgroundColor:'#ccc',borderRadius:3,font:{color:'red',weight:'bold',}}," +
                "doughnutlabel:{labels:[{text:'%total%',font:{size:20,weight:'bold'}},{text:'Total'}]}}}}";
        try {
            List<ReportDTO> reportDTOS = responseRepository.generateReportByQuestionId(questionDTO.getId());

            List<Integer> data = reportDTOS.stream()
                    .map(ReportDTO::getData)
                    .collect(Collectors.toList());

            List<String> labels = reportDTOS
                    .stream()
                    .map(ReportDTO::getLabel)
                    .map(s -> String.format("'%s'", s))
                    .collect(Collectors.toList());

            int total = data.stream().mapToInt(Integer::intValue).sum();

            pieChartUrl = pieChartUrl
                    .replace("%label%", labels.toString())
                    .replace("%data%",data.toString())
                    .replace("%total%", String.valueOf(total));
            String encodedURL = URLEncoder.encode(pieChartUrl, String.valueOf(StandardCharsets.UTF_8));
            pieChartUrl = "https://quickchart.io/chart?c=".concat(encodedURL).replaceAll("\\s","");

            URL url = new URL(pieChartUrl);

            return toByteStream(url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Delete Answers
     *
     * @param answerDTOS List of AnswerDTOs to be deleted
     * @return true if Answers deleted
     * @throws RemoteException
     */
    @Override
    public boolean deleteAnswers(List<AnswerDTO> answerDTOS) throws RemoteException {
        for (AnswerDTO answerDTO : answerDTOS) {
            try {
                List<Response> responseList = responseRepository.findByAnswerId(answerDTO.getId());
                for (Response response : responseList) {
                    responseRepository.delete(response.getId());
                }
                answerRepository.delete(answerDTO.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private byte[] toByteStream(InputStream inputStream) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            byte[] bytes = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytes)) > 0) {
                stream.write(bytes, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
        return stream.toByteArray();
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
