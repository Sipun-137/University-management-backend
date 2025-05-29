package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Branch;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// BranchDTO.java
public class BranchDTO {
    private Long id;
    private String name;
    private Long courseId;
    private String code;

    public BranchDTO(Branch branch) {
        this.id=branch.getId();
        this.courseId = branch.getCourse().getId();
        this.name = branch.getName();
        this.code= branch.getCode();
    }
    // Getters
}
