package com.sipun.UniversityBackend.exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sipun.UniversityBackend.academic.dto.BatchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.exam.model.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamResponseDTO {
    private Long id;
    private SubjectMinimalDTO subject;
    private SemesterMinimalDTO semester;
    private BatchMinimalDTO batch;
    private BranchMinimalDTO branch;
    private String examType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate examDate;

    private Double totalMarks;
    private Integer durationMinutes;
    private Boolean isPublished;
    private Integer version;
    private List<RubricDTO> rubrics;

    public ExamResponseDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = new SubjectMinimalDTO(exam.getSubject());
        this.semester = new SemesterMinimalDTO(exam.getSemester());
        this.batch = new BatchMinimalDTO(exam.getBatch());
        this.branch = new BranchMinimalDTO(exam.getBranch());
        this.examType = exam.getExamType().name();
        this.examDate = exam.getExamDate();
        this.totalMarks = exam.getTotalMarks();
        this.durationMinutes = exam.getDurationMinutes();
        this.version = exam.getVersion();
        this.isPublished = exam.getIsPublished();
//        this.rubrics = exam.getRubrics().stream()
//                .map(RubricDTO::new)
//                .toList();
    }
}
