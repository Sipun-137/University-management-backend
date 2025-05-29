package com.sipun.UniversityBackend.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentProfileDTO {
    @NotBlank
    private String name;
    @Pattern(regexp = "^\\d{10}$")
    private String phone;
    @Past
    private LocalDate dob;
    @NotBlank
    private String address;
    @NotNull
    private Long courseId; // Student selects course
    @NotNull
    private Long branchId; // Student selects branch
}
