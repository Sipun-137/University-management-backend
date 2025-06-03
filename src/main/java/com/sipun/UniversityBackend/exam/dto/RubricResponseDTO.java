package com.sipun.UniversityBackend.exam.dto;


import com.sipun.UniversityBackend.exam.enums.RubricStatus;
import com.sipun.UniversityBackend.exam.model.Rubric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RubricResponseDTO {
    private Long id;
    private String criteria;
    private Double maxMarks;
    private ExamDTO exam;
    private int questionNumber;
    private RubricStatus status;


    public RubricResponseDTO(Rubric rubric) {
        this.id = rubric.getId();
        this.criteria = rubric.getCriteria();
        this.maxMarks = rubric.getMaxMarks();
        this.exam = new ExamDTO(rubric.getExam());
        this.questionNumber = rubric.getQuestionNumber();
        this.status = rubric.getStatus();

    }

}
