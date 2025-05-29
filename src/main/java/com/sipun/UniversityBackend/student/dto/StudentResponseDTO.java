package com.sipun.UniversityBackend.student.dto;


import com.sipun.UniversityBackend.academic.dto.BatchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String emergencyContact;
    private String gender;
    private String address;
    private LocalDate dob;
    private String regdNo;
    private String rollNo;
    private String bloodGroup;
    private String course;
    private String nationality;
    private String profilePhotoUrl;
    private String academicStatus;

    // Academic Relationships
    private BatchMinimalDTO batch;
    private BranchMinimalDTO branch;
    private String section;
    private String currentSemester;

    // Guardian Details
    private GuardianDTO guardian;

    // Audit Fields
    private LocalDate createdAt;
    private LocalDate updatedAt;



}
