package com.jamith.rmi.repository;

import com.jamith.rmi.entity.User;

/**
 * @author Jamith Nimantha
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * get an instance of the User by email
     *
     * @param email email of the user
     * @return the instance of the User
     */
    User findByEmail(String email);
}
