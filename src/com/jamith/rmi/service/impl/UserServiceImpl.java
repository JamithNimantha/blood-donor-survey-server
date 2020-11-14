package com.jamith.rmi.service.impl;

import com.jamith.rmi.dto.UserDTO;
import com.jamith.rmi.entity.User;
import com.jamith.rmi.repository.UserRepository;
import com.jamith.rmi.repository.impl.UserRepositoryImpl;
import com.jamith.rmi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jamith Nimantha
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{

    private UserRepository userRepository;

    UserServiceImpl() throws RemoteException {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
    }

    /**
     * Save instance of user
     *
     * @param userDTO UserDTO
     * @return true if the instance of the user is saved
     * @throws Exception
     */
    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        User user = toEntity(userDTO);
        return userRepository.save(user);

    }

    /**
     * Register User
     *
     * @param userDTO UserDTO
     * @return true if user registered
     * @throws Exception
     */
    @Override
    public boolean registerUser(UserDTO userDTO) {
        userDTO.setType("USER");
        try {
            return saveUser(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create New Admin User
     *
     * @param userDTO Admin UserDTO
     * @return true if admin created
     */
    @Override
    public boolean createAdminUser(UserDTO userDTO) {
        userDTO.setType("ADMIN");
        try {
            return saveUser(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the instance of the User
     *
     * @param userDTO UserDTO
     * @return true if user updated
     * @throws Exception
     */
    @Override
    public boolean updateUser(UserDTO userDTO) throws RemoteException {
        User user = toEntity(userDTO);
        try {
            return userRepository.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete User
     *
     * @param id id of the User to be deleted
     * @return true if user deleted
     * @throws Exception
     */
    @Override
    public boolean deleteUser(Integer id) throws RemoteException {
        try {
            return userRepository.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Login validation for the user. Generate session If email and password are validated
     *
     * @param email email of the user
     * @param password password of the user
     * @return the generated session for the useer
     * @throws RemoteException
     */
    @Override
    public String login(String email, String password) throws RemoteException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            boolean equals = password.equals(user.getPassword());
            if (equals) {
                return "qwe" + Math.random();
            }
        }
        return null;
    }

    /**
     * logOut and destroy the user session
     *
     * @param cookie session cookie
     * @return true if session destroyed
     */
    @Override
    public boolean logout(String cookie) {
        return true;
    }

    /**
     * Convert UserDTO to User class type
     *
     * @param userDTO UserDTO to be converted
     * @return user as a entity
     */
    private User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setType(userDTO.getType());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Convert User Entity to UserDTO
     *
     * @param user User entity to be converted
     * @return userDTO as DTO
     */
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setType(user.getPassword());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
