package com.sipun.UniversityBackend.academic.controller;


import com.sipun.UniversityBackend.academic.dto.SemesterRequest;
import com.sipun.UniversityBackend.academic.dto.SemesterResponse;
import com.sipun.UniversityBackend.academic.service.SemesterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academic")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/semester")
    public ResponseEntity<SemesterResponse> create(@Valid @RequestBody SemesterRequest request) {
        return ResponseEntity.ok(semesterService.createSemester(request));
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterResponse>> getAll() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    @GetMapping("/semester/{id}")
    public ResponseEntity<SemesterResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(semesterService.getSemesterById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/semester/{id}")
    public ResponseEntity<SemesterResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SemesterRequest request
    ) {
        return ResponseEntity.ok(semesterService.updateSemester(id, request));
    }

    @DeleteMapping("/semester/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/semester/{semesterId}/mark-current")
    public ResponseEntity<Void> markAsCurrent(@PathVariable Long semesterId) {
        semesterService.markSemesterAsCurrent(semesterId);
        return ResponseEntity.ok().build();
    }

    // Get the current semester for a branch
    @GetMapping("/semester/current")
    public ResponseEntity<SemesterResponse> getCurrentSemester(
            @RequestParam Long branchId // Pass branch ID as query param
    ) {
        return ResponseEntity.ok(semesterService.getCurrentSemester(branchId));
    }
}

