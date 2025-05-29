package com.sipun.UniversityBackend.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CourseRequestDTO {
    @NotBlank(message = "Course name is required")
    private String name;

    @NotBlank(message = "Course code is required")
    private String code;

    @Positive(message = "Duration must be a positive number")
    private int durationInYears;
}