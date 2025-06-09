package com.sipun.UniversityBackend.auth.controller;

import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.auth.service.JwtService;
import com.sipun.UniversityBackend.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("register")
    public User registerUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getEmail());
                User dbUser = service.findByEmail(user.getEmail());

                response.put("role", dbUser.getRole());
                response.put("email", dbUser.getEmail());
                response.put("isVerified", dbUser.getStatus());
                response.put("token", token);
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, Object> request, Authentication authentication) {
        String oldPassword = (String) request.get("oldPassword");
        String newPassword = (String) request.get("newPassword");

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String email = user.getUsername();

        User updatedUser = userService.changePassword(email, oldPassword, newPassword);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("isVerified", updatedUser.getStatus());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
