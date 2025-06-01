package com.sipun.UniversityBackend.exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamCreationDTO {
    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Semester ID is required")
    private Long semesterId;

    @NotNull(message = "Batch ID is required")
    private Long batchId;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotBlank(message = "Exam type is required")
    @Pattern(regexp = "MID_SEM|FINAL", message = "Invalid exam type")
    private String examType;

    @Future(message = "Exam date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate examDate;

    @Positive(message = "Total marks must be positive")
    @Max(value = 200, message = "Marks cannot exceed 200")
    private Double totalMarks;

    @Positive(message = "Duration must be positive")
    @Min(value = 30, message = "Minimum duration is 30 minutes")
    @Max(value = 240, message = "Maximum duration is 4 hours (240 minutes)")
    Integer durationMinutes;

    private Boolean isPublished;

    @PositiveOrZero(message = "Version must be positive")
    private Integer version;
}
