package com.sipun.UniversityBackend.grievance.service;


import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.grievance.model.Grievance;
import com.sipun.UniversityBackend.grievance.model.GrievanceCategory;
import com.sipun.UniversityBackend.grievance.model.GrievanceStatsDTO;
import com.sipun.UniversityBackend.grievance.model.GrievanceStatus;
import com.sipun.UniversityBackend.grievance.repo.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrievanceService {
    @Autowired
    private GrievanceRepository grievanceRepository;

    @Autowired
    private UserRepo userRepository;

    public Grievance submitGrievance(Grievance grievance, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        grievance.setSubmittedBy(user);
        grievance.setSubmittedAt(LocalDateTime.now());
        grievance.setStatus(GrievanceStatus.PENDING);
        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getUserGrievances(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return grievanceRepository.findBySubmittedBy(user);
    }

    public List<Grievance> getAllGrievances() {
        return grievanceRepository.findAll();
    }

    public Grievance respondToGrievance(Long id, GrievanceStatus status, String response) {
        Grievance g = grievanceRepository.findById(id).orElseThrow();
        g.setStatus(status);
        g.setResponse(response);
        g.setUpdatedAt(LocalDateTime.now());
        return grievanceRepository.save(g);
    }

    public Optional<Grievance> getGrievanceById(Long id) {
        return grievanceRepository.findById(id);
    }

    public GrievanceStatsDTO getGrievanceStats() {
        List<Grievance> grievances = grievanceRepository.findAll();

        long total = grievances.size();

        Map<String, Long> statusCounts = grievances.stream()
                .collect(Collectors.groupingBy(g -> g.getStatus().name(), Collectors.counting()));

        Map<String, Long> categoryCounts = grievances.stream()
                .collect(Collectors.groupingBy(g -> g.getCategory().name(), Collectors.counting()));

        // Fill missing enums with 0 if not present
        for (GrievanceStatus status : GrievanceStatus.values()) {
            statusCounts.putIfAbsent(status.name(), 0L);
        }

        for (GrievanceCategory category : GrievanceCategory.values()) {
            categoryCounts.putIfAbsent(category.name(), 0L);
        }

        GrievanceStatsDTO dto = new GrievanceStatsDTO();
        dto.setTotal(total);
        dto.setStatusCounts(statusCounts);
        dto.setCategoryCounts(categoryCounts);

        return dto;
    }
}
