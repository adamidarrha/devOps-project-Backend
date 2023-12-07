package net.javaguides.springboot.dto;

import lombok.Data;

//the data type that we are going to get in the signUp endpoint
@Data
public class SignUpRequest {
    
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String username;
}
