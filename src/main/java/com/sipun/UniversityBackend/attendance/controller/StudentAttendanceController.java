package com.sipun.UniversityBackend.attendance.controller;

import com.sipun.UniversityBackend.attendance.dto.StudentAttendanceSummaryDTO;
import com.sipun.UniversityBackend.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/attendance/student")
public class StudentAttendanceController {

    @Autowired
    private AttendanceService studentAttendanceService;

//    @PostMapping("/summary")
//    public ResponseEntity<StudentAttendanceSummaryDTO> getStudentAttendanceSummary(@RequestBody StudentAttendanceFilterRequest filterRequest) {
//        StudentAttendanceSummaryDTO summary = studentAttendanceService.getStudentAttendanceSummary(filterRequest);
//        return ResponseEntity.ok(summary);
//    }
//
//    @PostMapping("/details")
//    public ResponseEntity<List<StudentAttendanceDetailDTO>> getStudentAttendanceDetails(@RequestBody StudentAttendanceFilterRequest filterRequest) {
//        List<StudentAttendanceDetailDTO> details = studentAttendanceService.getStudentAttendanceDetails(filterRequest);
//        return ResponseEntity.ok(details);
//    }
}
