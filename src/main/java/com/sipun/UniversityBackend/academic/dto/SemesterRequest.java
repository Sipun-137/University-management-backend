package com.sipun.UniversityBackend.academic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class SemesterRequest {
    @Min(value = 1, message = "Semester number must be at least 1")
    @Max(value = 8, message = "Semester number cannot exceed 8")
    private int number;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @JsonProperty("isCurrent") // Map JSON "isCurrent" to this field
    private boolean current;

    // Getters and Setters
}