package com.sipun.UniversityBackend.student.controller;

import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.student.dto.*;
import com.sipun.UniversityBackend.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Admin creates student (minimal data)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("student", studentService.createStudent(dto));
            response.put("success", true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(response);
    }

    // Admin assigns section/semester
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{studentId}/assign-section")
    public ResponseEntity<StudentResponseDTO> assignSection(@PathVariable Long studentId, @Valid @RequestBody SectionAssignmentDTO dto) {
        return ResponseEntity.ok(studentService.assignSection(studentId, dto));
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStudents() {
        Map<String, Object> response = new HashMap<>();
        List<StudentResponseDTO> students = studentService.findAll();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    @GetMapping("/{semesterId}/{sectionId}")
    public ResponseEntity<List<StudentMinimalDTO>> getStudentsBySemesterIdAndSectionId(@PathVariable Long semesterId, @PathVariable Long sectionId) {
        return new ResponseEntity<>(studentService.getStudentsBySemesterIdAndSectionId(semesterId, sectionId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-profile")
    public ResponseEntity<Map<String, Object>> myProfile(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();

        Long studentId = studentService.getStudentIdFromUserId(userId);


        Map<String, Object> response = new HashMap<>();
        response.put("profile", studentService.findById(studentId));
        response.put("success", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateStudent(@RequestBody StudentUpdateDTO dto, @PathVariable Long id) {

        studentService.updateStudent(id, dto);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message","Data updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
