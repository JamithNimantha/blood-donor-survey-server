package com.jamith.rmi.entity;

import com.jamith.rmi.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jamith Nimantha
 */

@Getter
@Setter
@ToString
@Entity(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "mobile_number")
    private String mobile;
    @Column(name = "type")
    private String type;
    @Column(name = "password")
    private String password;
    private String salt;

    /**
     * Convert User Entity to UserDTO
     *
     * @return userDTO as DTO
     */
    public UserDTO toDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(this.getId());
        dto.setFullName(this.getFullName());
        dto.setEmail(this.getEmail());
        dto.setMobile(this.getMobile());
        dto.setType(this.getPassword());
        dto.setPassword(this.getPassword());
        return dto;
    }
}
