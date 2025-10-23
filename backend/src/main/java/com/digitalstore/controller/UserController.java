package com.digitalstore.controller;

import com.digitalstore.model.LoginRequest;
import com.digitalstore.model.User;
import com.digitalstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return service.getAllUsers();
  }

  @PostMapping("/register")
  public ResponseEntity<String> createUser(@RequestBody User user) {
    try {
      User createdUser = service.createUser(user);
      return ResponseEntity.status(200).body("User Created");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("User creation failed " + e.getMessage());
    }
  }

  @PostMapping("/login")
  public User login(@RequestBody LoginRequest request) {
    return service.login(request.getUsername(), request.getPassword());
  }
}


