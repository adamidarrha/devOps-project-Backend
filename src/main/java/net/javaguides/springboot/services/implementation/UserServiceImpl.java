package net.javaguides.springboot.services.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.services.UserService;


//this service is important to get users by their username for login purposes and authorization of jwt
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
            }
        };
    }

}
