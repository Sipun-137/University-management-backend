package com.sipun.UniversityBackend.faculty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultyAssignmentRequestDTO {
    private Long semester;
    private Long batchId;
    private String academicYear;
    private String term;
}

