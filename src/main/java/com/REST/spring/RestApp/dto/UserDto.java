package com.REST.spring.RestApp.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
public class UserDto {  // объект для общения с клиентом для работы только с определнными полями которые необходимы

    @NotEmpty(message = "non empty name")
    private String firstname;

    @NotEmpty(message = "non empty name")
    @Size(min = 2,max = 15)
    private String lastname;
@Min(value = 0,message = "Age should be greater than 0")
    private int age;

    @Email
    @NotEmpty(message = "non empty email")
    private String email;


}
