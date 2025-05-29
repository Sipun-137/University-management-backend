package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Section;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class SectionDto {
    private Long id;
    private String name;
    private int currentStrength;
    private int maxStrength;
    private long batchId;
    private long branchId;

    public SectionDto(Section section) {
        this.id = section.getId();
        this.name = section.getName();
        this.currentStrength = section.getCurrentStrength();
        this.maxStrength = section.getMaxStrength();
        this.branchId=section.getBranch().getId();
        this.batchId=section.getBatch().getId();
    }

    // Getters
}