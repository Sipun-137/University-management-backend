package com.sipun.UniversityBackend.faculty.dto;

import com.sipun.UniversityBackend.student.model.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateFacultyDTO {
    private String name;
    private String email;
    private String address;
    private String bloodGroup;
    private Student.Gender gender;
    private String nationality;
    private Long branchId;
    private String designation;
    private String phone;
    private String emergencyContact;
    private LocalDate dob;

}