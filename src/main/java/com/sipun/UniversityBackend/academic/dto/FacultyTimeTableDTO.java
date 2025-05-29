package com.sipun.UniversityBackend.academic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacultyTimeTableDTO {
    private SubjectMinimalDTO subject;
    private SectionMinimalDTO section;
    private String branchName;
    private SemesterMinimalDTO semester;
    private TimeSlotDTO timeSlot;
}