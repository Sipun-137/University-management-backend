package com.sipun.UniversityBackend.exam.service;


import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.exam.dto.MarkerRequestDTO;
import com.sipun.UniversityBackend.exam.dto.MarkerResponseDTO;
import com.sipun.UniversityBackend.exam.model.Marker;
import com.sipun.UniversityBackend.exam.repo.MarkerRepo;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarkerService {

    @Autowired
    private MarkerRepo markerRepo;
    @Autowired
    private FacultyRepo facultyRepo;


    //create a new marker
    public MarkerResponseDTO createMarker(MarkerRequestDTO markerRequestDTO) {
        Faculty faculty = facultyRepo.findById(markerRequestDTO.getStaffId()).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        Marker marker = Marker.builder().faculty(faculty).fullName(faculty.getName()).isModerator(markerRequestDTO.getIsModerator()).build();
        Marker newMarker = markerRepo.save(marker);
        return new MarkerResponseDTO(newMarker);
    }
    //get all marker
    public List<MarkerResponseDTO> getAllMarkers() {
        List<Marker> markers = markerRepo.findAll();
        return markers.stream().map(MarkerResponseDTO::new).collect(Collectors.toList());
    }

    //get marker by id
    public MarkerResponseDTO getMarkerById(Long id) {
        Optional<Marker> marker = markerRepo.findById(id);
        if (marker.isPresent()) {
            return new MarkerResponseDTO(marker.get());
        } else {
            throw new ResourceNotFoundException("Marker not found");
        }
    }

    //delete marker by id
    public String deleteMarkerById(Long id) {
        markerRepo.deleteById(id);
        return "Marker deleted successfully";
    }

    //update marker by promoting or demoting the marker
    public MarkerResponseDTO updateMarker(Boolean isModerator, Long id) {
        Marker marker = markerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marker not found"));
        marker.setIsModerator(isModerator);
        markerRepo.save(marker);
        return new MarkerResponseDTO(marker);
    }
}
