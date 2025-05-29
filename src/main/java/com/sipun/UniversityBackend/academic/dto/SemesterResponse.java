package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Semester;
import com.sipun.UniversityBackend.academic.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterResponse {
    private Long id;
    private int number;
    private boolean current;
    private BranchMinimalDTO branch; // Changed to minimal DTO
    private List<Long> subjectIds;

    public SemesterResponse(Semester semester) {
        this.id = semester.getId();
        this.number = semester.getNumber();
        this.current = semester.isCurrent();
        this.branch = new BranchMinimalDTO(semester.getBranch());
        this.subjectIds = semester.getSubjects().stream()
                .map(Subject::getId)
                .toList();
    }
}