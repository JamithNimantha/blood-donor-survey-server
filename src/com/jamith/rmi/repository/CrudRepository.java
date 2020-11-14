package com.jamith.rmi.repository;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public interface CrudRepository<T, I> {

    /**
     * Save the instance of the Entity
     *
     * @param entity The Entity to be saved
     * @return true if instance of the Entity Successfully saved
     * @throws Exception
     */
    boolean save(T entity) throws Exception;

    /**
     * update the instance of the Entity
     *
     * @param entity The instance of the entity to be updated
     * @return true if instance of the Entity successfully updated
     * @throws Exception
     */
    boolean update(T entity) throws Exception;

    /**
     * delete the instance of the entity
     *
     * @param id id of the entity to be deleted
     * @return true if the instance of the Entity successfully deleted
     * @throws Exception
     */
    boolean delete(I id) throws Exception;

    /**
     * get the @Entity by the id
     *
     * @param id id of the entity to get
     * @return the instance of the entity
     * @throws Exception
     */
    T getOne(I id) throws Exception;

    /**
     * Get all the instances of the entity
     *
     * @return All the instance of the entity as List
     * @throws Exception
     */
    List<T> getAll() throws Exception;
}
