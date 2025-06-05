package com.sipun.UniversityBackend.exam.dto;


import com.sipun.UniversityBackend.exam.enums.MarkingProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkingAssignmentRequestDTO {
    private Long examId;
    private Long markerId;
    private MarkingProgress progress;


}
