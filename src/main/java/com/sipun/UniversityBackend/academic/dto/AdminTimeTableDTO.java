package com.sipun.UniversityBackend.academic.dto;


import com.sipun.UniversityBackend.faculty.dto.FacultyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminTimeTableDTO {
    private String sectionName;
    private String branchName;
    private SubjectMinimalDTO subject;
    private FacultyDTO faculty;
    private TimeSlotDTO timeSlot;
    private String academicYear;
}