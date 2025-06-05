package com.sipun.UniversityBackend.exam.dto;

import com.sipun.UniversityBackend.exam.model.MarkingAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkingAssignmentResponseDTO {
    private Long id;
    private ExamDTO exam;
    private MarkerDTO marker;

    public MarkingAssignmentResponseDTO(MarkingAssignment markingAssignment) {
        this.id = markingAssignment.getId();
        this.exam = new ExamDTO(markingAssignment.getExam());
        this.marker = new MarkerDTO(markingAssignment.getMarker());
    }

}
