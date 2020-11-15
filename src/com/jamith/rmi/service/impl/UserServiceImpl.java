package com.jamith.rmi.service.impl;

import com.jamith.rmi.dto.UserDTO;
import com.jamith.rmi.entity.User;
import com.jamith.rmi.repository.RepositoryFactory;
import com.jamith.rmi.repository.UserRepository;
import com.jamith.rmi.service.UserService;
import com.jamith.rmi.util.PasswordUtil;
import com.jamith.rmi.util.ToEntity;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{

    private UserRepository userRepository;

    UserServiceImpl() throws RemoteException {
        userRepository = RepositoryFactory.getInstance().RepoFactoryFor(RepositoryFactory.RepositoryTypes.USER);
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
        User user = ToEntity.toUserEntity(userDTO);
        System.out.println(user);
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
        System.out.println(userDTO);
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
            String salt = PasswordUtil.getSalt();
            String securePassword = PasswordUtil.generateSecurePassword(userDTO.getPassword(), salt);
            userDTO.setSalt(salt);
            userDTO.setPassword(securePassword);
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
        User user = ToEntity.toUserEntity(userDTO);

        try {
            String salt = PasswordUtil.getSalt();
            String securePassword = PasswordUtil.generateSecurePassword(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(securePassword);
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
            try {
                boolean equals = PasswordUtil.verifyUserPassword(password, user.getPassword(), user.getSalt());
                if (equals) {
                    return "qwe" + Math.random();
                }
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
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
    public boolean logout(String cookie) throws RemoteException {
        return true;
    }

    /**
     * Get all the Users as a UserDTO List
     *
     * @return all the Users
     */
    @Override
    public List<UserDTO> getAllUsers() throws RemoteException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> userList = userRepository.getAll();
            for (User user : userList) {
                UserDTO userDTO = user.toDTO();
                userDTOS.add(userDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTOS;
    }
}
