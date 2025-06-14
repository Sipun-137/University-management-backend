package com.sipun.UniversityBackend.auth.service;

import com.sipun.UniversityBackend.academic.exception.ConflictException;
import com.sipun.UniversityBackend.auth.exception.EmailAlreadyExistsException;
import com.sipun.UniversityBackend.auth.model.Status;
import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        user.setStatus(Status.UNVERIFIED);
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User changePassword(String email, String oldPassword, String newPassword) {
        User user = findByEmail(email);

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new ConflictException("Passwords don't match");
        }

        user.setPassword(encoder.encode(newPassword));
        user.setStatus(Status.VERIFIED);

        return repo.save(user);
    }
}
