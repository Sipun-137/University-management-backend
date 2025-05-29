package com.sipun.UniversityBackend.faculty.controller;

import com.sipun.UniversityBackend.faculty.dto.FacultyAssignmentRequestDTO;
import com.sipun.UniversityBackend.faculty.service.FacultyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty-assignments")

public class FacultyAssignmentController {

    @Autowired
    private FacultyAssignmentService facultyAssignmentService;

    @PostMapping("/assign")
    public ResponseEntity<String> assignFaculty(
            @RequestBody FacultyAssignmentRequestDTO dto) {
        try {
            facultyAssignmentService.assignFacultyToSubjects(dto.getSemester(), dto.getBatchId(), dto.getAcademicYear(),dto.getTerm());
            return ResponseEntity.ok("Faculty assignment completed successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }
}
