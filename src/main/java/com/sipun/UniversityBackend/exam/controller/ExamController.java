package com.sipun.UniversityBackend.exam.controller;

import com.sipun.UniversityBackend.exam.dto.ExamCreationDTO;
import com.sipun.UniversityBackend.exam.dto.ExamResponseDTO;
import com.sipun.UniversityBackend.exam.model.Exam;
import com.sipun.UniversityBackend.exam.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @PostMapping
    public ResponseEntity<ExamResponseDTO> createExam(@Valid @RequestBody ExamCreationDTO dto) {
        ExamResponseDTO createdExam = examService.createExam(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
    }
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        return ResponseEntity.ok(examService.updateExam(id, exam));
    }
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ExamResponseDTO>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(new ExamResponseDTO(examService.getExamById(id)));
    }
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @GetMapping("/batch/{batchId}/branch/{branchId}/semester/{semesterId}")
    public ResponseEntity<List<ExamResponseDTO>> getExamByBatchAndBranchAndSemester(@PathVariable Long batchId, @PathVariable Long branchId, @PathVariable Long semesterId) {
        return ResponseEntity.ok(examService.getExamByBatchAndBranchAndSemester(batchId, branchId, semesterId));
    }
}
