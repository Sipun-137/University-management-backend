package com.sipun.UniversityBackend.academic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchRequestDTO {
    private Long courseId;
    private int startYear;
    private int endYear;
}
