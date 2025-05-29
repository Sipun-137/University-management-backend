package com.sipun.UniversityBackend.faculty.dto;

public record FacultyAssignmentDTO(
        Long sectionId,
        String sectionName,
        Long subjectId,
        String subjectCode,
        String academicYear,
        String term,
        int weeklyHours
) {}
