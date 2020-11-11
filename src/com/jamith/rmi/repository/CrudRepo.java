package com.jamith.rmi.repository;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public interface CrudRepo<T, I> {

    public boolean save(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(I id) throws Exception;

    public List<T> getAll() throws Exception;
}
