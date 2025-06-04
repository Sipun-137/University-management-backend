package com.sipun.UniversityBackend.exam.controller;


import com.sipun.UniversityBackend.exam.dto.MarkerRequestDTO;
import com.sipun.UniversityBackend.exam.dto.MarkerResponseDTO;
import com.sipun.UniversityBackend.exam.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers")
public class MarkerController {

    @Autowired
    private MarkerService markerService;

    @PostMapping
    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    public ResponseEntity<MarkerResponseDTO> createMarker(@RequestBody MarkerRequestDTO markerRequestDTO) {
        return new ResponseEntity<>(markerService.createMarker(markerRequestDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<MarkerResponseDTO>> getAllMarkers() {
        return new ResponseEntity<>(markerService.getAllMarkers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseDTO> getMarker(@PathVariable Long id) {
        return new ResponseEntity<>(markerService.getMarkerById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarker(@PathVariable Long id) {
        return new ResponseEntity<>(markerService.deleteMarkerById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EXAM_CONTROLLER')")
    @PutMapping("/{id}")
    public ResponseEntity<MarkerResponseDTO> updateMarker(@PathVariable Long id, @RequestBody MarkerRequestDTO markerRequestDTO) {
        return new ResponseEntity<>(markerService.updateMarker(markerRequestDTO.getIsModerator(), id), HttpStatus.OK);
    }


}
