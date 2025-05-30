package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Batch;
import lombok.Data;
import lombok.Getter;


import java.util.List;

@Data
@Getter
public class BatchResponseDto{
    // Getters
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

}