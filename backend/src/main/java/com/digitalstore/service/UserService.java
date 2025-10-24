package com.digitalstore.service;

import com.digitalstore.model.Role;
import com.digitalstore.model.User;
import com.digitalstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserService(UserRepository repo, PasswordEncoder encoder ) {
    this.repo = repo;
    this.encoder = encoder;
  }

  public List<User> getAllUsers() {
    return repo.findAll();
  }

  public Optional<User> getUser(Long id) {
    return repo.findById(id);
  }

  public User createUser(User user) {
    user.setPassword(user.getPassword());
    return repo.save(user);
  }

  public User login(String username, String rawPassword) {
    User user = repo.findByUsername(username);
    if (user != null && rawPassword.equals(user.getPassword())) {
      return user;
    }
    return null;
  }
}

