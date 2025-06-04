package com.sipun.UniversityBackend.exam.dto;

import com.sipun.UniversityBackend.academic.dto.BatchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.exam.model.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {
    private Long id;
    private SubjectMinimalDTO subject;
    private SemesterMinimalDTO semester;
    private BatchMinimalDTO batch;
    private BranchMinimalDTO branch;
    private String examType;

    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = new SubjectMinimalDTO(exam.getSubject());
        this.semester = new SemesterMinimalDTO(exam.getSemester());
        this.batch = new BatchMinimalDTO(exam.getBatch());
        this.branch = new BranchMinimalDTO(exam.getBranch());
        this.examType = exam.getExamType().name();
    }
}
