package net.javaguides.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.SignUpRequest;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.services.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    //signUp endpoint, get the body of the request and take the field into a signUpRequest type
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        //pass it to authentication service that is going to save the user and return it
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<String> gett(){
        //pass it to authentication service that is going to save the user and return it
        return ResponseEntity.ok("hello");
    }

}
