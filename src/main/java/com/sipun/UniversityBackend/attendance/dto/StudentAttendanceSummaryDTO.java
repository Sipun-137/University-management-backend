package com.sipun.UniversityBackend.attendance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentAttendanceSummaryDTO {
    private Long studentId;
    private String studentName;
    private int totalSessions;
    private int present;
    private int absent;
    private int late;
    private int excuse;
    private double attendancePercentage;
}
