package com.sipun.UniversityBackend.attendance.controller;


import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.attendance.dto.AttendanceRequest;
import com.sipun.UniversityBackend.attendance.dto.FacultyScheduleDTO;
import com.sipun.UniversityBackend.attendance.dto.StudentAttendanceDTO;
import com.sipun.UniversityBackend.attendance.service.AttendanceService;
import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    @Autowired
    private FacultyRepo facultyRepo;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request,Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();

        Faculty faculty = facultyRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        request.setFacultyId(faculty.getId());
        service.markAttendance(request);
        return ResponseEntity.ok("Attendance marked successfully.");
    }

    // ✅ 2. Student views attendance for current semester
    @GetMapping("/student/{studentId}/semester/{semesterId}")
    public ResponseEntity<List<StudentAttendanceDTO>> getStudentAttendance(
            @PathVariable Long studentId,
            @PathVariable Long semesterId) {
        return ResponseEntity.ok(
                service.getStudentAttendanceForSemester(studentId, semesterId)
        );
    }

    @GetMapping("/faculty/today")
    public ResponseEntity<List<FacultyScheduleDTO>> getFacultySchedule(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();

        Faculty faculty = facultyRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        return new ResponseEntity<>(service.getFacultyClassesToday(faculty.getId()),HttpStatus.OK);
    }

    // ✅ 3. Faculty dashboard: get today's classes with attendance status
//    @GetMapping("/faculty/classes")
//    public ResponseEntity<List<FacultyClassAttendanceStatusDTO>> getFacultyClassAttendanceStatus(
//            @RequestParam Long facultyId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return ResponseEntity.ok(
//                attendanceService.getFacultyClassesWithAttendanceStatus(facultyId, date)
//        );
//    }
}
