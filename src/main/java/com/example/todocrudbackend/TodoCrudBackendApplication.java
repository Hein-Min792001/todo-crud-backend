package com.example.todocrudbackend;

import com.example.todocrudbackend.entity.Role;
import com.example.todocrudbackend.entity.User;
import com.example.todocrudbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoCrudBackendApplication {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    @Profile("test")
    public ApplicationRunner runner(){
        return r -> {
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            Role role2 = new Role();
            role2.setName("ROLE_USER");

            User user1 = new User();
            user1.setName("Mary");
            user1.setUsername("mary");
            user1.setPassword(passwordEncoder.encode("12345"));
            user1.setEmail("mary@gmail.com");

            User user2 = new User();
            user2.setName("Emma");
            user2.setUsername("emma");
            user2.setPassword(passwordEncoder.encode("12345"));
            user2.setEmail("emma@gmail.com");

            user1.getRoles().add(role1);
            user2.getRoles().add(role2);

            userRepository.save(user1);
            userRepository.save(user2);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(TodoCrudBackendApplication.class, args);
    }

}
