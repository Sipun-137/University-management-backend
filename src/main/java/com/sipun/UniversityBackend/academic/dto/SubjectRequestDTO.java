package com.sipun.UniversityBackend.academic.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubjectRequestDTO {
    @NotBlank(message = "Subject name is required")
    private String name;

    @Positive(message = "Credits must be a positive number")
    private int credits;

    @NotBlank(message = "Subject code is required")
    private String subjectCode;

    @NotNull(message = "Semester ID is required")
    private Long semesterId;
}