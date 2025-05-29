package com.sipun.UniversityBackend.academic.service;

import com.sipun.UniversityBackend.academic.dto.*;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Semester;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.repo.BranchRepo;
import com.sipun.UniversityBackend.academic.repo.SemesterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private BranchRepo branchRepo;

    public SemesterResponse createSemester(SemesterRequest request) {
        Branch branch = branchRepo.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        Semester semester = new Semester();
        semester.setNumber(request.getNumber());
        semester.setBranch(branch);
        semester.setCurrent(request.isCurrent());

        Semester savedSemester = semesterRepo.save(semester);
        return convertToSemesterResponse(savedSemester);
    }

    public List<SemesterResponse> getAllSemesters() {
        List<Semester> semesters = semesterRepo.findAllWithSubjects(); // Custom method to fetch subjects
        return semesters.stream()
                .map(this::convertToSemesterResponse)
                .toList();
    }

    public SemesterResponse getSemesterById(Long id) {
        Semester semester = semesterRepo.findByIdWithSubjects(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        return convertToSemesterResponse(semester);
    }

    @Transactional
    public SemesterResponse updateSemester(Long id, SemesterRequest request) {


        Semester existing = semesterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        // Update basic fields
        existing.setNumber(request.getNumber());
        Branch newBranch = branchRepo.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        existing.setBranch(newBranch);

//        System.out.println("[DEBUG] Request isCurrent: " + request.isCurrent());
//        System.out.println("[DEBUG] Existing branch ID: " + existing.getBranch().getId());
//        System.out.println("[DEBUG] New branch ID: " + newBranch.getId());

        // Handle isCurrent update
        if (request.isCurrent()) {
//            System.out.println("[DEBUG] Unmarking semesters in branch: " + newBranch.getId());
            // Unmark other active semesters in the new branch
            List<Semester> activeSemesters = semesterRepo.findByBranchIdAndIsCurrentTrue(newBranch.getId());
            activeSemesters.forEach(s -> {
//                System.out.println("[DEBUG] Unmarking semester: " + s.getId());
                if (!s.getId().equals(id)) { // Exclude the current semester being updated
                    s.setCurrent(false);
                    semesterRepo.save(s);
                }
            });
        }
        existing.setCurrent(request.isCurrent());

        Semester updated = semesterRepo.save(existing);
        return convertToSemesterResponse(updated);
    }

    private SemesterResponse convertToSemesterResponse(Semester semester) {
        BranchMinimalDTO branchMinimal = new BranchMinimalDTO(
                semester.getBranch().getId(),
                semester.getBranch().getName(),
                new CourseMinimalDTO(semester.getBranch().getCourse())
        );

        List<Long> subjectIds = Optional.ofNullable(semester.getSubjects())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Subject::getId)
                .toList();

        return new SemesterResponse(
                semester.getId(),
                semester.getNumber(),
                semester.isCurrent(),
                branchMinimal,
                subjectIds
        );
    }

    public void deleteSemester(Long id) {
        Semester semester = semesterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        // Optional: Check for dependencies
        if (!semester.getSubjects().isEmpty()) {
            throw new IllegalStateException("Cannot delete semester with linked subjects");
        }

        semesterRepo.delete(semester); // More explicit than deleteById
    }


    public void markSemesterAsCurrent(Long semesterId) {
        Semester semester = semesterRepo.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        // Get all active semesters for the branch
        List<Semester> activeSemesters = semesterRepo.findByBranchIdAndIsCurrentTrue(
                semester.getBranch().getId()
        );

        // Unmark all active semesters (excluding the current one if already marked)
        activeSemesters.forEach(s -> {
            if (!s.getId().equals(semesterId)) { // Avoid unnecessary update if already false
                s.setCurrent(false);
                semesterRepo.save(s);
            }
        });

        // Mark the selected semester as current
        semester.setCurrent(true);
        semesterRepo.save(semester);
    }

    public SemesterResponse getCurrentSemester(Long branchId) {
        List<Semester> activeSemesters = semesterRepo.findByBranchIdAndIsCurrentTrue(branchId);

        if (activeSemesters.isEmpty()) {
            throw new RuntimeException("No active semester found");
        }

        // Return the first (and only) active semester
        return convertToSemesterResponse(activeSemesters.get(0));
    }
}
