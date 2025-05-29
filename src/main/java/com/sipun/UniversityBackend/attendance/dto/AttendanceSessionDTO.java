package com.sipun.UniversityBackend.attendance.dto;

import com.sipun.UniversityBackend.academic.dto.Shift;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceSessionDTO {
    private Long id;
    private LocalDate date;
    private String subjectName;
    private String facultyName;
    private String sectionName;
    private Shift shift;
    private int period;
}
