package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private String code;
    private int durationInYears;
    private List<Long> branchIds; // Include only IDs of related entities
    private List<Long> batchIds;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.code=course.getCode();
        this.durationInYears = course.getDurationInYears();
    }
}
