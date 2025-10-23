package com.digitalstore.config;

import com.digitalstore.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/users/**").permitAll()
        .requestMatchers("/api/v1/products/upload").hasRole("ADMIN")
        .requestMatchers("/api/v1/products/**").permitAll()
        .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> {
      var user = userRepository.findByUsername(username);
      if (user == null) {
        throw new UsernameNotFoundException("User not found: " + username);
      }
      // Passwords are plain text for simplicity
      return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(String.valueOf(user.getRole())) // ADMIN or VIEWER
        .build();
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // No encoding for simplicity
    return NoOpPasswordEncoder.getInstance();
  }
}
