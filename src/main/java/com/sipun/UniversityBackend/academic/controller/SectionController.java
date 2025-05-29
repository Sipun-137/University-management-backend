package com.sipun.UniversityBackend.academic.controller;


import com.sipun.UniversityBackend.academic.dto.SectionDto;
import com.sipun.UniversityBackend.academic.dto.SectionResponseDTO;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academic")
public class SectionController {

    @Autowired
    private SectionService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/section")
    public Section create(@RequestBody SectionDto section) {
        return service.save(section);
    }

    @GetMapping("/sections")
    public ResponseEntity<List<SectionResponseDTO>> getAll() {
        List<Section> sections = service.getAll();
        List<SectionResponseDTO> response = sections.stream()
                .map(SectionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/section/{id}")
    public Section get(@PathVariable Long id) {
        return service.getById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/section/{id}")
    public Section update(@PathVariable Long id, @RequestBody SectionDto section) {
        return service.update(id, section);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/section/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
