package com.sipun.UniversityBackend.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StudentAttendanceDTO {
    private String subjectName;
    private String subjectCode;
    private int totalClasses;
    private int present;
    private int absent;
    private double percentage;
}
