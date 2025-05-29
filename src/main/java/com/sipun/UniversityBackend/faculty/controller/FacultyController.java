package com.sipun.UniversityBackend.faculty.controller;

import com.sipun.UniversityBackend.faculty.dto.CreateFacultyDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyAssignmentDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyAssignmentResponseDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyResponseDTO;
import com.sipun.UniversityBackend.faculty.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FacultyController.java
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @PostMapping
    public ResponseEntity<FacultyResponseDTO> createFaculty(
            @Valid @RequestBody CreateFacultyDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyService.createFaculty(dto));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<FacultyResponseDTO> updateFaculty(
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateFacultyDTO dto
//    ) {
//        return ResponseEntity.ok(facultyService.updateFaculty(id, dto));
//    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<FacultyResponseDTO> getFacultyById(@PathVariable Long id) {
//        return ResponseEntity.ok(facultyService.getFacultyById(id));
//    }
//
    @PostMapping("/{facultyId}/assignments")
    public ResponseEntity<FacultyResponseDTO> addAssignment(
            @PathVariable Long facultyId,
            @Valid @RequestBody FacultyAssignmentDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyService.addAssignment(facultyId, dto));
    }


    @GetMapping("/assignments")
    public ResponseEntity<List<FacultyAssignmentResponseDTO>> getAssignments() {
        return new ResponseEntity<>(facultyService.getAllAssignments(), HttpStatus.OK);
    }

    @PostMapping("/add-faculties")
    public ResponseEntity<?> loadListOfFaculty(@RequestBody List<CreateFacultyDTO> faculties) {
        facultyService.autoCreateFaculty(faculties);
        return new ResponseEntity<>("successfully faculty addd", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllFaculty() {
        return new ResponseEntity<>(facultyService.findAll(), HttpStatus.OK);
    }
//    @GetMapping("/branch/{branchId}")
//    public ResponseEntity<List<FacultyResponseDTO>> getFacultyByBranch(
//            @PathVariable Long branchId
//    ) {
//        return ResponseEntity.ok(facultyService.getFacultyByBranch(branchId));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
//        facultyService.deleteFaculty(id);
//        return ResponseEntity.noContent().build();
//    }
}