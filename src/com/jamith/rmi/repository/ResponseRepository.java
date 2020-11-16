package com.jamith.rmi.repository;

import com.jamith.rmi.entity.Response;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public interface ResponseRepository extends CrudRepository<Response, Integer> {

    List<Response> findAllResponsesByEmail(String email) throws Exception;
}
