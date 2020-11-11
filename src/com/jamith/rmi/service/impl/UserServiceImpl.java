package com.jamith.rmi.service.impl;

import com.jamith.rmi.dto.UserDTO;
import com.jamith.rmi.entity.User;
import com.jamith.rmi.repository.UserRepo;
import com.jamith.rmi.repository.impl.UserRepoImpl;
import com.jamith.rmi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jamith Nimantha
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{

    private UserRepo userRepo;

    UserServiceImpl() throws RemoteException {
        if (userRepo == null) {
            userRepo = new UserRepoImpl();
        }
    }


    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        return false;
    }

    @Override
    public boolean registerUser(UserDTO userDTO) {
        System.out.println(userDTO);
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setType("USER");
        user.setPassword(userDTO.getPassword());
        System.out.println(user);
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws Exception {
        return false;
    }

    @Override
    public boolean deleteUser(UserDTO userDTO) throws Exception {
        return false;
    }
}
