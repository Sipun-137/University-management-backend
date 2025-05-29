package com.sipun.UniversityBackend.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBranchDTO {
    @NotBlank(message = "Branch name is required")
    @Size(max = 100, message = "Branch name must be less than 100 characters")
    private String name;

    @NotNull(message = "Course ID is required")
    private Long courseId;

}