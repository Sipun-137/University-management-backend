package com.sipun.UniversityBackend.academic.service;

import com.sipun.UniversityBackend.academic.dto.BranchDTO;
import com.sipun.UniversityBackend.academic.dto.BranchResponseDTO;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Course;
import com.sipun.UniversityBackend.academic.repo.BranchRepo;
import com.sipun.UniversityBackend.academic.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BranchService {

    @Autowired
    private BranchRepo repo;

    @Autowired
    private CourseRepo courseRepo;


    public Branch addBranch(BranchDTO branchdto){
        Course course = courseRepo.findById(branchdto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Branch branch=Branch.builder()
                .name(branchdto.getName())
                .course(course)
                .code(branchdto.getCode())
                .build();
        return repo.save(branch);
    }

    public List<Branch> getAllBranch(){
        return  repo.findAll();
    }


    public Branch getBranchById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + id));
    }

    public BranchResponseDTO updateBranch(Long id, BranchDTO dto) {
        // Find existing branch
        Branch existingBranch = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + id));

        // Update fields
        if(dto.getName() != null) {
            existingBranch.setName(dto.getName());
        }

        if(dto.getCourseId() != null) {
            Course newCourse = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + dto.getCourseId()));
            existingBranch.setCourse(newCourse);
        }

        // Save updated branch
        Branch updatedBranch = repo.save(existingBranch);

        // Convert to Response DTO
        return new BranchResponseDTO(updatedBranch);
    }


    public void deleteBranch(Long id) {
        if(!repo.existsById(id)) {
            throw new NoSuchElementException("Branch not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
