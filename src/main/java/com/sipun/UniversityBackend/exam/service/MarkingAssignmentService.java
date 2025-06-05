package com.sipun.UniversityBackend.exam.service;


import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.exam.dto.MarkingAssignmentRequestDTO;
import com.sipun.UniversityBackend.exam.dto.MarkingAssignmentResponseDTO;
import com.sipun.UniversityBackend.exam.model.Exam;
import com.sipun.UniversityBackend.exam.model.Marker;
import com.sipun.UniversityBackend.exam.model.MarkingAssignment;
import com.sipun.UniversityBackend.exam.repo.ExamRepository;
import com.sipun.UniversityBackend.exam.repo.MarkerRepo;
import com.sipun.UniversityBackend.exam.repo.MarkingAssignmentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarkingAssignmentService {


    @Autowired
    private MarkingAssignmentRepo markingAssignmentRepo;

    @Autowired
    private ExamRepository examRepo;
    @Autowired
    private MarkerRepo markerRepo;

    public MarkingAssignmentResponseDTO addMarkingAssignment(MarkingAssignmentRequestDTO markingAssignmentRequestDTO) {

        Exam exam = examRepo.findById(markingAssignmentRequestDTO.getExamId()).orElseThrow(() -> new ResourceNotFoundException("Exam with id:  " + markingAssignmentRequestDTO.getExamId() + " not found"));
        Marker marker = markerRepo.findById(markingAssignmentRequestDTO.getMarkerId()).orElseThrow(() -> new ResourceNotFoundException("Unable to find the Marker with id : " + markingAssignmentRequestDTO.getMarkerId()));
        MarkingAssignment markingAssignment = MarkingAssignment.builder()
                .markingProgress(markingAssignmentRequestDTO.getProgress())
                .exam(exam)
                .marker(marker)
                .build();
        MarkingAssignment newMarkingAssignment = markingAssignmentRepo.save(markingAssignment);
        return new MarkingAssignmentResponseDTO(newMarkingAssignment);
    }

    //create a method to get all the marker for an exam
    public List<MarkingAssignmentResponseDTO> getMarkingAssignmentsByExamId(Long examId) {
        List<MarkingAssignment> markingAssignments = markingAssignmentRepo.findByExam_Id(examId);
        return markingAssignments.stream()
                .map(MarkingAssignmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MarkingAssignmentResponseDTO> getMarkingAssignmentsByMarkerId(Long markerId) {
        List<MarkingAssignment> markingAssignments = markingAssignmentRepo.findByMarker_Id(markerId);
        return markingAssignments.stream()
                .map(MarkingAssignmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MarkingAssignmentResponseDTO> getMyMarkingAssignment(String userId) {
        return markingAssignmentRepo.findByMarker_Faculty_User_Id(userId).stream()
                .map(MarkingAssignmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public String DeleteMarkingAssignment(Long id) {
        markingAssignmentRepo.deleteById(id);
        return "Marking Assignment with id: " + id + " has been deleted";
    }




}
