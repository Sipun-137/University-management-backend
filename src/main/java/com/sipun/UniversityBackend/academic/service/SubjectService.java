package com.sipun.UniversityBackend.academic.service;


import com.sipun.UniversityBackend.academic.dto.*;
import com.sipun.UniversityBackend.academic.exception.ConflictException;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.Semester;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.repo.SemesterRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class SubjectService {

    private final SubjectRepo subjectRepo;
    private final SemesterRepo semesterRepo;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, SemesterRepo semesterRepo) {
        this.subjectRepo = subjectRepo;
        this.semesterRepo = semesterRepo;
    }

    // Create a Subject
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {
        // Validate semester exists
        Semester semester = semesterRepo.findById(dto.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));

        // Check for duplicate subject code
        if (subjectRepo.existsBySubjectCode(dto.getSubjectCode())) {
            throw new ConflictException("Subject code already exists");
        }

        // Convert DTO to Entity manually
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCredits(dto.getCredits());
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setSemester(semester);

        Subject savedSubject = subjectRepo.save(subject);
        return convertToSubjectResponseDTO(savedSubject);
    }

    // Get all Subjects
    public List<SubjectResponseDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepo.findAllWithSemester();
        return subjects.stream()
                .map(this::convertToSubjectResponseDTO)
                .toList();
    }

    // Get a Subject by ID
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = subjectRepo.findByIdWithSemester(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return convertToSubjectResponseDTO(subject);
    }

    // Update a Subject
    public SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO dto) {
        Subject existing = subjectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        // Check for duplicate subject code (if changed)
        if (!existing.getSubjectCode().equals(dto.getSubjectCode())
                && subjectRepo.existsBySubjectCode(dto.getSubjectCode())) {
            throw new ConflictException("Subject code already exists");
        }

        // Update fields manually
        existing.setName(dto.getName());
        existing.setCredits(dto.getCredits());
        existing.setSubjectCode(dto.getSubjectCode());

        // Update semester if changed
        if (!existing.getSemester().getId().equals(dto.getSemesterId())) {
            Semester newSemester = semesterRepo.findById(dto.getSemesterId())
                    .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
            existing.setSemester(newSemester);
        }

        Subject updated = subjectRepo.save(existing);
        return convertToSubjectResponseDTO(updated);
    }

    // Delete a Subject
    public void deleteSubject(Long id) {
        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        subjectRepo.delete(subject);
    }

    // Convert Subject to SubjectResponseDTO manually
//    private SubjectResponseDTO convertToSubjectResponseDTO(Subject subject) {
//        SubjectResponseDTO dto = new SubjectResponseDTO();
//        dto.setId(subject.getId());
//        dto.setName(subject.getName());
//        dto.setCredits(subject.getCredits());
//        dto.setSubjectCode(subject.getSubjectCode());
//
//        // Convert nested Semester to SemesterResponseDTO
//        dto.setSemester(convertToSemesterResponseDTO(subject.getSemester()));
//        return dto;
//    }

    // Convert Semester to SemesterResponseDTO manually
//    private SemesterResponse convertToSemesterResponseDTO(Semester semester) {
//        SemesterResponse semesterDTO = new SemesterResponse();
//        semesterDTO.setId(semester.getId());
//        semesterDTO.setNumber(semester.getNumber());
//        semesterDTO.setCurrent(semester.isCurrent());
//
//        // Convert nested Branch to BranchResponseDTO (if needed)
//        BranchResponseDTO branchDTO = new BranchResponseDTO();
//        branchDTO.setId(semester.getBranch().getId());
//        branchDTO.setName(semester.getBranch().getName());
//        semesterDTO.setBranch(branchDTO);
//
//        return semesterDTO;
//    }

    // Convert Subject to SubjectResponseDTO
    private SubjectResponseDTO convertToSubjectResponseDTO(Subject subject) {
        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .credits(subject.getCredits())
                .subjectCode(subject.getSubjectCode())
                .semester(convertToSemesterMinimalDTO(subject.getSemester()))
                .build();
    }

    // Convert Semester to SemesterMinimalDTO
    private SemesterMinimalDTO convertToSemesterMinimalDTO(Semester semester) {
        return new SemesterMinimalDTO(
                semester.getId(),
                semester.getNumber(),
                semester.isCurrent(),
                new BranchMinimalDTO(
                        semester.getBranch().getId(),
                        semester.getBranch().getName(),
                        new CourseMinimalDTO(semester.getBranch().getCourse())
                )
        );
    }
}