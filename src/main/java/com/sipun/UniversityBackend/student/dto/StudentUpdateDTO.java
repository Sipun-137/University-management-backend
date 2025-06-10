package com.sipun.UniversityBackend.student.dto;

import com.sipun.UniversityBackend.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateDTO {
    private String name;
    private String phone;
    private String emergencyContact;
    private Student.Gender gender;
    private String address;
    private LocalDate dob;

    private String bloodGroup;
    private String nationality;

    // Guardian Details
    private Student.Guardian guardian;
}
