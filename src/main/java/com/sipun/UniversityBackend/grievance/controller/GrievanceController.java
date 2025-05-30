package com.sipun.UniversityBackend.grievance.controller;

import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import com.sipun.UniversityBackend.grievance.model.Grievance;
import com.sipun.UniversityBackend.grievance.model.GrievanceStatsDTO;
import com.sipun.UniversityBackend.grievance.model.GrievanceStatus;
import com.sipun.UniversityBackend.grievance.model.ResponseDTO;
import com.sipun.UniversityBackend.grievance.service.GrievanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    @PostMapping
    public ResponseEntity<Grievance> submit(@RequestBody Grievance grievance, Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();
        log.info("username={}", username);
        return ResponseEntity.ok(grievanceService.submitGrievance(grievance, principal.getUsername()));
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    @GetMapping("/my")
    public ResponseEntity<List<Grievance>> getMyGrievances(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(grievanceService.getUserGrievances(principal.getUsername()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Grievance>> getAll() {
        return ResponseEntity.ok(grievanceService.getAllGrievances());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Grievance> getById(@PathVariable Long id) {
        return grievanceService.getGrievanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/respond")
    public ResponseEntity<Grievance> respond(
            @PathVariable Long id,
            @RequestBody ResponseDTO dto
    ) {
        String status = dto.getStatus();
        String response = dto.getResponse();
        return ResponseEntity.ok(grievanceService.respondToGrievance(id, GrievanceStatus.valueOf(status), response));
    }

    @GetMapping("/stats")
    public ResponseEntity<GrievanceStatsDTO> getStats() {
        return new ResponseEntity<>(grievanceService.getGrievanceStats(), HttpStatus.OK);
    }
}
