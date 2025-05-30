package com.sipun.UniversityBackend.auth.repo;

import com.sipun.UniversityBackend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
