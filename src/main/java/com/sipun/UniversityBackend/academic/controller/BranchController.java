package com.sipun.UniversityBackend.academic.controller;


import com.sipun.UniversityBackend.academic.dto.BranchDTO;
import com.sipun.UniversityBackend.academic.dto.BranchResponseDTO;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academic")
public class BranchController {

    @Autowired
    private BranchService service;

    @PostMapping("/branch")
    public ResponseEntity<?> createBranch(@RequestBody BranchDTO dto) {
        return new ResponseEntity<>(service.addBranch(dto),HttpStatus.OK);
    }


    @GetMapping("/branches")
    public ResponseEntity<List<BranchResponseDTO>> getBranchDetails() {
        List<Branch> branches = service.getAllBranch();
        List<BranchResponseDTO> response = branches.stream()
                .map(BranchResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/branch/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable Long id) {
        try {
            Branch branch = service.getBranchById(id);
            return ResponseEntity.ok(new BranchResponseDTO(branch));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Branch not found with id: " + id);
        }
    }

    // Update
    @PutMapping("/branch/{id}")
    public ResponseEntity<?> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO dto
    ) {
        try {
            BranchResponseDTO updatedBranch = service.updateBranch(id, dto);
            return ResponseEntity.ok(updatedBranch);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Branch not found with id: " + id);
        }
    }

    // Delete
    @DeleteMapping("/branch/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {
        try {
            service.deleteBranch(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Branch not found with id: " + id);
        }
    }


}
