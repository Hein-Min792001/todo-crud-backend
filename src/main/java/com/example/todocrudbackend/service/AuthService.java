package com.example.todocrudbackend.service;

import com.example.todocrudbackend.dto.LoginDto;
import com.example.todocrudbackend.dto.RegisterDto;
import com.example.todocrudbackend.entity.Role;
import com.example.todocrudbackend.entity.User;
import com.example.todocrudbackend.exception.ToDoApiException;
import com.example.todocrudbackend.repository.RoleRepository;
import com.example.todocrudbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String login(LoginDto loginDto){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUsernameOrEmail(),
                                loginDto.getPassword()
                        )
                );
        return "Login Successfully!";
    }


    public String register(RegisterDto registerDto){
        //check username is already exist in database
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new ToDoApiException(HttpStatus.BAD_REQUEST,
                    "Username already exists.");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new ToDoApiException(HttpStatus.BAD_REQUEST,
                    "Email already exists!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles =
                new HashSet<>();
        Role userRole = roleRepository.findRoleByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        return "Register Successfully!";
    }
}
