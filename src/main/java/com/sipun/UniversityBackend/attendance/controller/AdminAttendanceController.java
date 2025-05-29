package com.sipun.UniversityBackend.attendance.controller;

import com.sipun.UniversityBackend.attendance.dto.AttendanceRecordDTO;
import com.sipun.UniversityBackend.attendance.dto.AttendanceSessionDTO;
import com.sipun.UniversityBackend.attendance.dto.StudentAttendanceSummaryDTO;
import com.sipun.UniversityBackend.attendance.model.AttendanceStatus;
import com.sipun.UniversityBackend.attendance.service.AdminAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
@RequiredArgsConstructor
public class AdminAttendanceController {

    private final AdminAttendanceService adminAttendanceService;

    // 1. View all sessions (by batch start year and branch)
    @GetMapping("/sessions")
    public ResponseEntity<List<AttendanceSessionDTO>> getSessions(
            @RequestParam int startYear,
            @RequestParam Long branchId) {
        return ResponseEntity.ok(adminAttendanceService.getSessions(startYear, branchId));
    }

    // 2. View students in a session
    @GetMapping("/sessions/{sessionId}/records")
    public ResponseEntity<List<AttendanceRecordDTO>> getSessionRecords(@PathVariable Long sessionId) {
        return ResponseEntity.ok(adminAttendanceService.getRecordsForSession(sessionId));
    }

    // 3. View single student summary
    @GetMapping("/students/{studentId}/summary")
    public ResponseEntity<StudentAttendanceSummaryDTO> getStudentSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(adminAttendanceService.getStudentAttendanceSummary(studentId));
    }

    // 4. Update attendance record
    @PutMapping("/records/{recordId}")
    public ResponseEntity<Void> updateRecord(
            @PathVariable Long recordId,
            @RequestParam AttendanceStatus status,
            @RequestParam(required = false) String remarks) {
        adminAttendanceService.updateAttendanceStatus(recordId, status, remarks);
        return ResponseEntity.noContent().build();
    }
}

