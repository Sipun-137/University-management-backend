package com.sipun.UniversityBackend.faculty.dto;

public record UpdateFacultyDTO(
        String firstName,
        String lastName,
        String email,
        Long branchId,
        boolean active
) {}
