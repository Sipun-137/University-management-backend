package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Batch;

import java.util.List;

public class BatchResponseDto{
    private Long id;
    private int startYear;
    private int endYear;
    private CourseMinimalDTO course;
    private Long courseId;
    private List<SectionDto> sections;


    // Constructor from Batch entity
    public BatchResponseDto(Batch batch) {
        this.id = batch.getId();
        this.startYear = batch.getStartYear();
        this.endYear = batch.getEndYear();
        this.courseId=batch.getCourse().getId();
        this.course = new CourseMinimalDTO(batch.getCourse());
        this.sections=batch.getSections().stream()
                .map(SectionDto::new)
                .toList();
    }

    // Getters
    public Long getId() { return id; }
    public int getStartYear() { return startYear; }
    public int getEndYear() { return endYear; }
    public CourseMinimalDTO getCourse() { return course; }
    public Long getCourseId(){return courseId;}
    public List<SectionDto> getSections() { return sections; }
}