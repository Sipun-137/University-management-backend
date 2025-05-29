package com.sipun.UniversityBackend.attendance.dto;

import com.sipun.UniversityBackend.academic.dto.SectionMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.TimeSlotDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultyScheduleDTO {

    private SubjectMinimalDTO subject;
    private SectionMinimalDTO section;
    private SemesterMinimalDTO semester;
    private TimeSlotDTO timeSlot;
    private boolean attendanceTaken;

}
