package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDTO {
    private Long id;
    private String name;
    private CourseDTO course;
    private List<SemesterResponse> semesters; // Use DTOs instead of entities

    public BranchResponseDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.course = new CourseDTO(branch.getCourse());
        this.semesters = branch.getSemesters().stream()
                .map(SemesterResponse::new)
                .toList();
    }
}
