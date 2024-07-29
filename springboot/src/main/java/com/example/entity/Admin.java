package com.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description admin
 * @author br
 * @date 2024-05-31
 */

@Entity
@Data
@Table(name="admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * username
     */
    @Column(name="username")
    private String username;

    /**
     * password
     */

    @Column(name="password")
    private String password;

    /**
     * admin_name
     */
    @Column(name="name")
    private String name;

    /**
     * created_at
     */
    @Column(name="created_time")
    private String createdTime;

    /**
     * updated_at
     */
    @Column(name="updated_time")
    private String updatedTime;

    /**
     * role
     */
    @Column(name="role")
    private String role;

    @Transient
    private String token;
    public Admin() {
    }

}
