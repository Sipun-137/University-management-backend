package com.sipun.UniversityBackend.academic.service;


import com.sipun.UniversityBackend.academic.dto.CourseDTO;
import com.sipun.UniversityBackend.academic.dto.CourseRequestDTO;
import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Course;
import com.sipun.UniversityBackend.academic.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepository;

    public CourseDTO saveCourse(CourseRequestDTO dto) {
        Course course=Course.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .durationInYears(dto.getDurationInYears())
                .build();
        Course newcourse =courseRepository.save(course);
        return convertToCourseDTO(newcourse);
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToCourseDTO)
                .toList();
    }

    public CourseDTO getCourseById(Long id) {
        Course course=courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToCourseDTO(course);
    }

    public CourseDTO updateCourse(Long id, Course updatedCourse) {
        CourseDTO ucourse = getCourseById(id);
        Course course=Course.builder()
                .name(updatedCourse.getName())
                .durationInYears(updatedCourse.getDurationInYears())
                .build();
        course.setName(updatedCourse.getName());
        course.setDurationInYears(updatedCourse.getDurationInYears());
        return convertToCourseDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseDTO convertToCourseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .durationInYears(course.getDurationInYears())
                .branchIds(course.getBranches().stream()
                        .map(Branch::getId)
                        .toList())
                .batchIds(course.getBatches().stream()
                        .map(Batch::getId)
                        .toList())
                .build();
    }
}

