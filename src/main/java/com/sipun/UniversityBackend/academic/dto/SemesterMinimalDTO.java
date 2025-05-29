package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterMinimalDTO {
    private Long id;
    private int number;
    private boolean current;
    private BranchMinimalDTO branch;

    public SemesterMinimalDTO(Semester semester) {
        this.id = semester.getId();
        this.number = semester.getNumber();
        this.current = semester.isCurrent();
        this.branch = new BranchMinimalDTO(semester.getBranch());
    }
}