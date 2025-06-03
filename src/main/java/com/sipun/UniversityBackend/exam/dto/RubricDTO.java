package com.sipun.UniversityBackend.exam.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RubricDTO {
    private Long id;
    private Integer questionNumber;
    private String criteria;
    private Double maxMarks;
}
