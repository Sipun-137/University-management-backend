package com.sipun.UniversityBackend.auth.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // ✅ internal technical primary key

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private Role role;
    private Status status;

    public enum Role {
        ADMIN,
        STUDENT,
        FACULTY,
        EXAM_CONTROLLER
    }
}







