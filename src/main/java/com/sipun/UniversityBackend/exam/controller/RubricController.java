package com.sipun.UniversityBackend.exam.controller;

import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.exam.dto.RubricCreateDTO;
import com.sipun.UniversityBackend.exam.dto.RubricResponseDTO;
import com.sipun.UniversityBackend.exam.service.RubricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rubric")
public class RubricController {

    @Autowired
    private RubricService rubricService;

    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @PostMapping("/exam/{examId}")
    public ResponseEntity<RubricResponseDTO> createRubric(@PathVariable Long examId, @RequestBody RubricCreateDTO rubric, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();

        RubricResponseDTO created = rubricService.createRubric(rubric, examId, userId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasRole('EXAM_CONTROLLER') or hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<List<RubricResponseDTO>> getAllRubricsByExamId(@PathVariable Long examId) {
        return new ResponseEntity<>(rubricService.getRubricsByExamId(examId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    public ResponseEntity<String> deleteRubric(@PathVariable Long id) {
        rubricService.deleteRubric(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    public ResponseEntity<RubricResponseDTO> updateRubric(@PathVariable Long id, @RequestBody RubricCreateDTO rubric) {
        return new ResponseEntity<>(rubricService.updateRubric(id, rubric), HttpStatus.OK);
    }


}
