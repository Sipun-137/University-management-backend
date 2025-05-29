package com.sipun.UniversityBackend.student.dto;

import com.sipun.UniversityBackend.student.model.Student;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequestDTO {
    // Student Fields
    private String name;
    private String email;
    private String phone;
    private String emergencyContact;
    private Student.Gender gender;
    private String address;
    private LocalDate dob;



    // Academic References
    @NotNull
    private Long batchId;
    @NotNull
    private Long branchId;
    @NotNull
    private Long courseId;

    private String bloodGroup;
    private String nationality;

    // Guardian Details
    private Student.Guardian guardian;
}
