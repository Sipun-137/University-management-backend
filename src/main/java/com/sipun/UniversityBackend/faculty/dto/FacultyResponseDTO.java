package com.sipun.UniversityBackend.faculty.dto;

import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String employeeId;
    private String designation;
    private BranchMinimalDTO department;
    private String address;
    private String bloodGroup;
    private String nationality;
    private String emergencyContact;
    private Student.Gender gender;
    private LocalDate dob;
    private String phone;

    public FacultyResponseDTO (Faculty faculty){
        this.id= faculty.getId();
        this.name= faculty.getName();
        this.employeeId= faculty.getEmployeeId();
        this.email= faculty.getEmail();
        this.designation=faculty.getDesignation().name();
        this.department=new BranchMinimalDTO(faculty.getDepartment());
        this.address= faculty.getAddress();
        this.bloodGroup= faculty.getBloodGroup();
        this.nationality= faculty.getNationality();
        this.emergencyContact= faculty.getEmergencyContact();
        this.gender=faculty.getGender();
        this.dob=faculty.getDob();
        this.phone= faculty.getPhone();

    }
}
