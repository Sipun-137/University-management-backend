package com.sipun.UniversityBackend.auth.repo;

import com.sipun.UniversityBackend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
