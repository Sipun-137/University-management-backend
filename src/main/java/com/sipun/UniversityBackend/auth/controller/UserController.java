package com.sipun.UniversityBackend.auth.controller;

import com.sipun.UniversityBackend.auth.exception.EmailAlreadyExistsException;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.service.MailService;
import com.sipun.UniversityBackend.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService emailService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User createUserDto) {

            User user = userService.saveUser(createUserDto);
            emailService.sendUserCredentials(user.getEmail(), createUserDto.getPassword());

            return ResponseEntity.ok("USer created and email send successfully");

    }


}
