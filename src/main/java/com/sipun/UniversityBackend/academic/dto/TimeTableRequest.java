package com.sipun.UniversityBackend.academic.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableRequest {
    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotBlank(message = "Academic year is required (e.g., 2024-25)")
    private String academicYear;

}