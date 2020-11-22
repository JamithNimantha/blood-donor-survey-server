package com.jamith.rmi.repository;

import com.jamith.rmi.dto.ReportDTO;
import com.jamith.rmi.entity.Response;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public interface ResponseRepository extends CrudRepository<Response, Integer> {

    /**
     *  Get all the response by user email
     *
     * @param email user email
     * @return List of Response
     * @throws Exception
     */
    List<Response> findAllResponsesByEmail(String email) throws Exception;

    /**
     * Generate Report by Question id
     *
     * @param id Question ID
     * @return List of Report Data
     * @throws Exception
     */
    List<ReportDTO> generateReportByQuestionId(Integer id) throws Exception;

    /**
     * Find Responses by Answer
     *
     * @param id Answer ID
     * @return List of Response
     */
    List<Response> findByAnswerId(Integer id);


}
