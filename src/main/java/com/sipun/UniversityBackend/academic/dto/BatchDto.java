package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Batch;
import lombok.Data;


@Data
public class BatchDto {
    private Long id;
    private int startYear;
    private int endYear;
    private Long CourseId;
    private String courseName;

    public BatchDto(Batch batch) {
        this.id=batch.getId();
        this.startYear = batch.getStartYear();
        this.endYear = batch.getEndYear();
        this.CourseId=batch.getCourse().getId();
        this.courseName=batch.getCourse().getName();
    }
    // Getters
}