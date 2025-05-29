package com.sipun.UniversityBackend.academic.dto;


import com.sipun.UniversityBackend.academic.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDTO {
    private Long id;
    private String name;
    private int credits;
    private String subjectCode;
    private SemesterMinimalDTO semester; // Changed to minimal DTO

    public SubjectResponseDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.credits = subject.getCredits();
        this.subjectCode = subject.getSubjectCode();
        this.semester = new SemesterMinimalDTO(subject.getSemester());
    }
}