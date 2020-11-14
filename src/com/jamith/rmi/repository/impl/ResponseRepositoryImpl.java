package com.jamith.rmi.repository.impl;

import com.jamith.rmi.entity.Response;
import com.jamith.rmi.repository.ResponseRepository;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class ResponseRepositoryImpl implements ResponseRepository {
    /**
     * Save the instance of the Entity
     *
     * @param entity The Entity to be saved
     * @return true if instance of the Entity Successfully saved
     * @throws Exception
     */
    @Override
    public boolean save(Response entity) throws Exception {
        return false;
    }

    /**
     * update the instance of the Entity
     *
     * @param entity The instance of the entity to be updated
     * @return true if instance of the Entity successfully updated
     * @throws Exception
     */
    @Override
    public boolean update(Response entity) throws Exception {
        return false;
    }

    /**
     * delete the instance of the entity
     *
     * @param id id of the entity to be deleted
     * @return true if the instance of the Entity successfully deleted
     * @throws Exception
     */
    @Override
    public boolean delete(Integer id) throws Exception {
        return false;
    }

    /**
     * get the @Entity by the id
     *
     * @param id id of the entity to get
     * @return the instance of the entity
     * @throws Exception
     */
    @Override
    public Response getOne(Integer id) throws Exception {
        return null;
    }

    /**
     * Get all the instances of the entity
     *
     * @return All the instance of the entity as List
     * @throws Exception
     */
    @Override
    public List<Response> getAll() throws Exception {
        return null;
    }
}
