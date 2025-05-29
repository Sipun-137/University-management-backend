package com.sipun.UniversityBackend.academic.service;

import com.sipun.UniversityBackend.academic.dto.BatchRequestDTO;
import com.sipun.UniversityBackend.academic.dto.BatchResponseDto;
import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Course;
import com.sipun.UniversityBackend.academic.repo.BatchRepo;
import com.sipun.UniversityBackend.academic.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {

    @Autowired
    private BatchRepo repo;

    @Autowired
    private CourseRepo courseRepo;




    public Batch save(BatchRequestDTO dto) {
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Batch batch=Batch.builder()
                .course(course)
                .endYear(dto.getEndYear())
                .startYear(dto.getStartYear())
                .build();

        return repo.save(batch);
    }

    public List<BatchResponseDto> getAll() {
        return repo.findAll()
                .stream()
                .map(BatchResponseDto::new)
                .toList();

    }

    public Batch getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    public Batch update(Long id, Batch batch) {
        Batch existing = getById(id);
        existing.setStartYear(batch.getStartYear());
        existing.setEndYear(batch.getEndYear());
        existing.setCourse(batch.getCourse());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

