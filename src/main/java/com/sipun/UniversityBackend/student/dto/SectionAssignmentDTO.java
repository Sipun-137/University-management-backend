package com.sipun.UniversityBackend.student.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SectionAssignmentDTO {
    @NotNull
    private Long sectionId;
    @NotNull
    private Long semesterId;
}
