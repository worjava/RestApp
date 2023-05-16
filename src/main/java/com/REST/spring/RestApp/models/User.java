package com.REST.spring.RestApp.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "persons")
@Data
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    @NotEmpty(message = "non empty name")
    private String firstname;
    @Column(name = "lastname")
    @NotEmpty(message = "non empty name")
    @Size(min = 2,max = 15)
    private String lastname;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    @Email
    @NotEmpty(message = "non empty email")
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @Column(name = "created_who")
    @NotEmpty
    private String createdWho;

    public User() {
    }


}






