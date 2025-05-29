package com.sipun.UniversityBackend.attendance.dto;

import com.sipun.UniversityBackend.academic.dto.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AttendanceRequest {
    private Long sectionId;
    private Long subjectId;
    private Long facultyId;
    private Long semesterId;
    private LocalDate date;
    private int period;
    private Shift shift;
    private List<StudentStatusDTO> studentAttendance;
}

