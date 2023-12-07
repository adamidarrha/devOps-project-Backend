package net.javaguides.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//we check if there is already an admin
		User user = userRepository.findByRole(Role.ADMIN);
		if(user == null){
			//if not we create one with username admin and password admin
			user = new User();

			user.setRole(Role.ADMIN);
			user.setEmail("AdminEmail@gmail.com");
			user.setUsername("admin");
			user.setFirstName("adminFirstName");
			user.setLastName("adminLastName");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}

}
