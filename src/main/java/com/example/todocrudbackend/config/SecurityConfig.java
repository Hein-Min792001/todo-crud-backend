package com.example.todocrudbackend.config;

import com.example.todocrudbackend.security.CustomUserDetailsService;
import com.example.todocrudbackend.security.JwtAuthenticationEntryPoint;
import com.example.todocrudbackend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails john = User.builder()
//                .username("John")
//                .password(passwordEncoder.encode("12345"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("Admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(john, admin);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(c ->
//                        c.requestMatchers(HttpMethod.POST, "/api/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE,"/api/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PATCH,"/api/**")
//                                .hasAnyRole("ADMIN","USER")
                        c.requestMatchers(HttpMethod.OPTIONS,"/**")
                                .permitAll()
                        .requestMatchers("/api/auth/**")
                                .permitAll()
//                                requestMatchers(HttpMethod.GET, "/api/**")
//                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
