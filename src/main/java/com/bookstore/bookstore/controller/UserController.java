package com.bookstore.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookstore.bookstore.dto.UserResponse;
import com.bookstore.bookstore.dto.LoginRequest;
import com.bookstore.bookstore.entity.User;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.dto.LoginResponse;
import com.bookstore.bookstore.security.JwtService;
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;

	public UserController(UserService userService, JwtService jwtService) {
	    this.userService = userService;
	    this.jwtService = jwtService;
	}

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        User registeredUser = userService.registerUser(user);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        LoginResponse loginResponse = new LoginResponse(
                token,
                userResponse
        );

        return ResponseEntity.ok(loginResponse);
    }
    
}