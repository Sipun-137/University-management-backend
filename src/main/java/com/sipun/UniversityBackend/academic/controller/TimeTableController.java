package com.sipun.UniversityBackend.academic.controller;


import com.sipun.UniversityBackend.academic.dto.AdminTimeTableDTO;
import com.sipun.UniversityBackend.academic.dto.FacultyTimeTableDTO;
import com.sipun.UniversityBackend.academic.dto.SectionTimeTableDTO;
import com.sipun.UniversityBackend.academic.dto.TimeTableRequest;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.service.TimeTableQueryService;
import com.sipun.UniversityBackend.academic.service.TimeTableService;
import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import com.sipun.UniversityBackend.student.repo.StudentRepo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;


// TimeTableController.java
@Slf4j
@RestController
@RequestMapping("/api/timetable")

public class TimeTableController {
    @Autowired
    private TimeTableService timetableService;

    @Autowired
    private TimeTableQueryService timeTableQueryService;

    @Autowired
    private FacultyRepo facultyRepo;

    @Autowired
    private StudentRepo studentRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public ResponseEntity<?> generateTimetable(
            @Valid @RequestBody TimeTableRequest request
    ) {
        timetableService.generateTimeTable(request.getBranchId(), request.getAcademicYear());
        return ResponseEntity.ok("scuccess");
    }


    // ============================
    // Faculty Endpoints
    // ============================

    @GetMapping("/faculty")
    public ResponseEntity<List<FacultyTimeTableDTO>> getFacultyTimeTable(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();
        log.info("facultyId={}", userId);

        Faculty faculty = facultyRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        log.info("facultyId={}", faculty.getId());

        return ResponseEntity.ok(timeTableQueryService.getTimeTableForFaculty(faculty.getId()));
    }

    @GetMapping("/faculty/{day}")
    public ResponseEntity<List<FacultyTimeTableDTO>> getFacultyTimeTableByDay( @PathVariable DayOfWeek day,Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();


        Faculty faculty = facultyRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        return ResponseEntity.ok(timeTableQueryService.getTimeTableForFacultyByDay(faculty.getId(), day));
    }

    // ============================
    // Student (Section) Endpoints
    // ============================

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-timetable")
    public ResponseEntity<List<SectionTimeTableDTO>> getSectionTimeTable(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();

        Long StudentId = studentRepo.findByUser_Id(userId).orElseThrow(()->new ResourceNotFoundException("Stduent not found")).getId();
        Long SectionId = studentRepo.findById(StudentId).orElseThrow(()->new ResourceNotFoundException("Student not found")).getSection().getId();
        log.info("sectionId={}", SectionId);
        return ResponseEntity.ok(timeTableQueryService.getTimeTableForSection(SectionId));
    }

    @GetMapping("/section/{sectionId}/day/{day}")
    public ResponseEntity<List<SectionTimeTableDTO>> getSectionTimeTableByDay(@PathVariable Long sectionId, @PathVariable DayOfWeek day) {
        return ResponseEntity.ok(timeTableQueryService.getTimeTableForSectionByDay(sectionId, day));
    }

    @GetMapping("/section/{sectionId}/year/{academicYear}")
    public ResponseEntity<List<SectionTimeTableDTO>> getSectionTimeTableByYear(@PathVariable Long sectionId, @PathVariable String academicYear) {
        return ResponseEntity.ok(timeTableQueryService.getTimeTableBySectionAndAcademicYear(sectionId, academicYear));
    }

    // ============================
    // Admin Endpoints
    // ============================

    @GetMapping("/admin/all")
    public ResponseEntity<List<AdminTimeTableDTO>> getAllTimeTables() {
        return ResponseEntity.ok(timeTableQueryService.getAllTimeTables());
    }

    @GetMapping("/admin/branch/{branchId}/year/{academicYear}")
    public ResponseEntity<List<AdminTimeTableDTO>> getTimeTableByBranchAndYear(@PathVariable Long branchId, @PathVariable String academicYear) {
        return ResponseEntity.ok(timeTableQueryService.getTimeTableByBranchAndAcademicYear(branchId, academicYear));
    }
}
