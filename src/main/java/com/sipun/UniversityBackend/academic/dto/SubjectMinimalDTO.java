package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectMinimalDTO {
    private Long id;
    private String name;
    private String subjectCode;
    private int weeklyHours;

    public SubjectMinimalDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.subjectCode=subject.getSubjectCode();
        this.weeklyHours= subject.getWeeklyHours();
    }
}
