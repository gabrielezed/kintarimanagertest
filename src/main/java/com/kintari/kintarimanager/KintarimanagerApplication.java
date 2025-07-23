package com.kintari.kintarimanager;

import com.kintari.kintarimanager.model.User;
import com.kintari.kintarimanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KintarimanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KintarimanagerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("password")); // Usa sempre il PasswordEncoder!
				admin.setRoles("ROLE_ADMIN,ROLE_USER");
				userRepository.save(admin);
				System.out.println(">>> Utente ADMIN creato con password 'password'");
			}

			if (userRepository.findByUsername("user").isEmpty()) {
				User user = new User();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("password"));
				user.setRoles("ROLE_USER");
				userRepository.save(user);
				System.out.println(">>> Utente USER creato con password 'password'");
			}
		};
	}
}