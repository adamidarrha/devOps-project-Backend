package net.javaguides.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    @GetMapping("/me")
    public ResponseEntity<String> gett(){
        //pass it to authentication service that is going to save the user and return it
        return ResponseEntity.ok("hello");
    }
}
