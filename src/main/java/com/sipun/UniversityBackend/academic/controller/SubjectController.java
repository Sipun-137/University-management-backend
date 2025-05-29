package com.sipun.UniversityBackend.academic.controller;

import com.sipun.UniversityBackend.academic.dto.SubjectRequestDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectResponseDTO;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/academic")
public class SubjectController {

    @Autowired
    private SubjectService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subject")
    public ResponseEntity<SubjectResponseDTO> create(@Valid @RequestBody SubjectRequestDTO dto) {
        return ResponseEntity.ok(service.createSubject(dto));
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllSubjects());
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<SubjectResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSubjectById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/subject/{id}")
    public ResponseEntity<SubjectResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequestDTO dto
    ) {
        return ResponseEntity.ok(service.updateSubject(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}