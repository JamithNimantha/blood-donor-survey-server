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
import java.net.HttpURLConnection;
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

    private static final String USER_AGENT_CHROME = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2919.83 Safari/537.36";

    private static final String QUICK_CHART_API_URL = "https://quickchart.io/chart?c=";

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
            Integer integer = questionRepository.saveQuestion(question);
            Question one = questionRepository.getOne(integer);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(one);
                answerRepository.save(answer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        List<Answer> answerList = answerRepository.findAllByQuestionId(id);
        for (Answer answer : answerList) {
            List<Response> responseList = responseRepository.findByAnswerId(answer.getId());
            for (Response response : responseList) {
                responseRepository.delete(response.getId());
            }
            answerRepository.delete(answer.getId());
        }
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
            for (Question question : questionList) {
                questionDTOS.add(question.toDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // Template of the Query Parameters
        String pieChartUrl = "{type:'pie',data:{labels:%label%,datasets:[{data:%data%}]},options:{plugins:" +
                "{datalabels:{display:true,backgroundColor:'#ccc',borderRadius:3,font:{color:'red',weight:'bold',}," +
                "formatter:(value)=>{return value+'%';}}}}}";
        try {
            List<ReportDTO> reportDTOS = responseRepository.generateReportByQuestionId(questionDTO.getId());

            int total = reportDTOS.stream().mapToInt(ReportDTO::getData).sum();

            List<Integer> data = reportDTOS.stream()
                    .map(reportDTO -> reportDTO.getData() * 100 / total)
                    .collect(Collectors.toList());

            List<String> labels = reportDTOS
                    .stream()
                    .map(ReportDTO::getLabel)
                    .map(s -> String.format("'%s'", s))
                    .collect(Collectors.toList());

            pieChartUrl = pieChartUrl
                    .replace("%label%", labels.toString())
                    .replace("%data%", data.toString());

            String encodedURL = URLEncoder.encode(pieChartUrl, String.valueOf(StandardCharsets.UTF_8));

            pieChartUrl = QUICK_CHART_API_URL.concat(encodedURL).replaceAll("\\s", "");

            return sendGetRequestToQuickChartAPI(pieChartUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    /**
     * Convert InputSteam to Byte Array.
     *
     * @param inputStream Image InputStream
     * @return Byte Array
     */
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

    /**
     * Send GET Request to QUICK CHART API and Get Report Image
     *
     * @param queryParameters encoded URL
     * @return Byte Array
     * @throws IOException
     */
    private byte[] sendGetRequestToQuickChartAPI(String queryParameters) throws IOException {
        URL url = new URL(queryParameters);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT_CHROME);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                byte[] bytes = new byte[4096];
                int bytesRead;
                while ((bytesRead = connection.getInputStream().read(bytes)) > 0) {
                    stream.write(bytes, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new byte[0];
            }
            return stream.toByteArray();
        } else {
            System.out.println("Error Occurred !" + responseCode);
        }
        return new byte[0];
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
