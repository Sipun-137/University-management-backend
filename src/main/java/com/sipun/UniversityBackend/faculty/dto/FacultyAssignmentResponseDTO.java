package com.sipun.UniversityBackend.faculty.dto;

import com.sipun.UniversityBackend.academic.dto.SectionMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultyAssignmentResponseDTO {
    private Long id;
    private FacultyResponseDTO faculty;
    private SectionMinimalDTO section;
    private SubjectMinimalDTO subject;
    private String academicYear;
    private String term;
    private int weeklyHours;

    public FacultyAssignmentResponseDTO(FacultyAssignment assignment){
        this.id= assignment.getId();
        this.faculty=new FacultyResponseDTO(assignment.getFaculty());
        this.section=new SectionMinimalDTO(assignment.getSection());
        this.subject=new SubjectMinimalDTO(assignment.getSubject());
        this.academicYear= assignment.getAcademicYear();
        this.term= assignment.getTerm();
        this.weeklyHours= assignment.getWeeklyHours();
    }
}


