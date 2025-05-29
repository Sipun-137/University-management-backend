package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponseDTO {
    private Long id;
    private String name;
    private int maxStrength;
    private int currentStrength;
    private BatchMinimalDTO batch; // Use minimal DTO
    private BranchMinimalDTO branch; // Use minimal DTO
    private List<SubjectMinimalDTO> subjects;

    public SectionResponseDTO(Section section) {
        this.id = section.getId();
        this.name = section.getName();
        this.maxStrength = section.getMaxStrength();
        this.currentStrength = section.getCurrentStrength();
        this.batch = new BatchMinimalDTO(section.getBatch());
        this.branch = new BranchMinimalDTO(section.getBranch());
        this.subjects = section.getSubjects().stream()
                .map(SubjectMinimalDTO::new)
                .toList();
    }
}