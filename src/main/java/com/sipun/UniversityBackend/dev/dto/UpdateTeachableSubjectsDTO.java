package com.sipun.UniversityBackend.dev.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTeachableSubjectsDTO {
    private List<Long> subjectIds;
}
