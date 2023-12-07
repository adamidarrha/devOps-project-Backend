package net.javaguides.springboot.services;

import net.javaguides.springboot.dto.SignUpRequest;
import net.javaguides.springboot.model.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);
}
