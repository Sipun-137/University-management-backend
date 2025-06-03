package com.sipun.UniversityBackend.exam.dto;

import com.sipun.UniversityBackend.exam.enums.RubricStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RubricCreateDTO {
    private String criteria;
    private int questionNumber;
    private Double maxMarks;
    private RubricStatus status;
}
