package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMinimalDTO {
    private Long id;
    private String name;

    public CourseMinimalDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}