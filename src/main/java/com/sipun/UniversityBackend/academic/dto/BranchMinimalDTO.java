package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchMinimalDTO {
    private Long id;
    private String name;
    private CourseMinimalDTO course;

    public BranchMinimalDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.course = new CourseMinimalDTO(branch.getCourse());
    }
}
