package com.jamith.rmi.dto;

import com.jamith.rmi.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jamith Nimantha
 */
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String fullName;
    private String email;
    private String mobile;
    private String type;
    private String password;
    private String salt;

    /**
     * Convert UserDTO to User class type
     *
     * @return user as a entity
     */
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setMobile(this.mobile);
        user.setType(this.type);
        user.setPassword(this.password);
        user.setSalt(salt);
        return user;
    }
}
