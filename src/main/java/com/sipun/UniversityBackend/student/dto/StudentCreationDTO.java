package com.sipun.UniversityBackend.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentCreationDTO {
    @NotBlank
    @Email
    private String email;
    private String name; // Optional
}
