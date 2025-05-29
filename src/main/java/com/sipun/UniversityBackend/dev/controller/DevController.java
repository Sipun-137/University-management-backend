package com.sipun.UniversityBackend.dev.controller;


import com.sipun.UniversityBackend.dev.dto.UpdateTeachableSubjectsDTO;
import com.sipun.UniversityBackend.dev.service.DevService;
import com.sipun.UniversityBackend.student.dto.StudentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dev")
public class DevController {

    @Autowired
    private DevService facultyService;


    @PutMapping("/{facultyId}/teachable-subjects")
    public ResponseEntity<String> updateTeachableSubjects(
            @PathVariable Long facultyId,
            @RequestBody UpdateTeachableSubjectsDTO dto) {
        facultyService.updateTeachableSubjects(facultyId, dto.getSubjectIds());
        return ResponseEntity.ok("Teachable subjects updated successfully.");
    }



    @PostMapping("/add-students")
    public ResponseEntity<?> addStudents(@RequestBody List<StudentRequestDTO> students) {
        return new ResponseEntity<>(facultyService.AddStudents(students), HttpStatus.OK);
    }


}
