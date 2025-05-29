package com.sipun.UniversityBackend.attendance.dto;

import com.sipun.UniversityBackend.attendance.model.AttendanceStatus;
import lombok.Data;

@Data
public class StudentStatusDTO {
    private Long studentId;
    private AttendanceStatus  status;
    private String remarks;
}
