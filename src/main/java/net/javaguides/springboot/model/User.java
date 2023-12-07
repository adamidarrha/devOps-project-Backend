package net.javaguides.springboot.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Email(message = "wrong email format")
    @jakarta.validation.constraints.NotBlank(message = "Email is required")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the roles/authorities of the user
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // Return the username (in this case, email) of the user
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the user's account is not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the user's account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the user's credentials are not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return true if the user is enabled
        return true;
    }
}
