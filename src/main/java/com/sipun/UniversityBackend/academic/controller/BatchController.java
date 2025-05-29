package com.sipun.UniversityBackend.academic.controller;


import com.sipun.UniversityBackend.academic.dto.BatchDto;
import com.sipun.UniversityBackend.academic.dto.BatchRequestDTO;
import com.sipun.UniversityBackend.academic.dto.BatchResponseDto;
import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academic")
public class BatchController {


    @Autowired
    private BatchService service;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/batch")
    public ResponseEntity<?> create(@RequestBody BatchRequestDTO batch) {
        return new ResponseEntity<>(service.save(batch), HttpStatus.CREATED);
    }



    @GetMapping("/batches")
    public List<BatchResponseDto> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/batch/{id}")
    public Batch get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/batch/{id}")
    public Batch update(@PathVariable Long id, @RequestBody Batch batch) {
        return service.update(id, batch);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/batch/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
