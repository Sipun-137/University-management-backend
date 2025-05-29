package com.sipun.UniversityBackend.student.dto;

import com.sipun.UniversityBackend.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentMinimalDTO {

    private Long id;
    private String name;
    private String regdNo;
    private String rollNo;


    public StudentMinimalDTO(Student stduent){
        this.id = stduent.getId();
        this.name = stduent.getName();
        this.regdNo = stduent.getRegdNo();
        this.rollNo = stduent.getRollNo();
    }
}
