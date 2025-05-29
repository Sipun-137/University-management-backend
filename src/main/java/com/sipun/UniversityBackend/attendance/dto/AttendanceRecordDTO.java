package com.sipun.UniversityBackend.attendance.dto;

import com.sipun.UniversityBackend.attendance.model.AttendanceStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceRecordDTO {
    private Long studentId;
    private String studentName;
    private AttendanceStatus status;
    private String remarks;
}
