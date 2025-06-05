package com.sipun.UniversityBackend.exam.controller;

import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.exam.dto.MarkingAssignmentRequestDTO;
import com.sipun.UniversityBackend.exam.dto.MarkingAssignmentResponseDTO;
import com.sipun.UniversityBackend.exam.service.MarkingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markingassignment")
public class MarkingAssignmentController {


    @Autowired
    private MarkingAssignmentService markingAssignmentService;

    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @PostMapping
    public ResponseEntity<MarkingAssignmentResponseDTO> addMarkingAssignment(@RequestBody MarkingAssignmentRequestDTO markingAssignmentRequestDTO) {
        return new ResponseEntity<>(markingAssignmentService.addMarkingAssignment(markingAssignmentRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasRole('EXAM_CONTROLLER') or hasRole('ADMIN')")
    public ResponseEntity<List<MarkingAssignmentResponseDTO>> getMarkingAssignmentsByExamId(@PathVariable Long examId) {
        return new ResponseEntity<>(markingAssignmentService.getMarkingAssignmentsByExamId(examId), HttpStatus.OK);
    }

    @GetMapping("/marker/{markerId}")
    @PreAuthorize("hasRole('EXAM_CONTROLLER') or hasRole('ADMIN')")
    public ResponseEntity<List<MarkingAssignmentResponseDTO>> getMarkingAssignmentsByMarkerId(@PathVariable Long markerId) {
        return ResponseEntity.ok(markingAssignmentService.getMarkingAssignmentsByMarkerId(markerId));
    }

    @GetMapping("/my-assignments")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<List<MarkingAssignmentResponseDTO>> getMyMarkingAssignments(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();
        return new ResponseEntity<>(markingAssignmentService.getMyMarkingAssignment(userId), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarkingAssignment(@PathVariable Long id) {
        return new ResponseEntity<>(markingAssignmentService.DeleteMarkingAssignment(id), HttpStatus.OK);
    }


}
