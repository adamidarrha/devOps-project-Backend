package net.javaguides.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;


import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    User findByRole(Role role);

}
