package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionMinimalDTO {
    private Long id;
    private String name;
    private int maxStrength;
    private int currentStrength;

    public SectionMinimalDTO(Section section){
        this.id= section.getId();
        this.name= section.getName();
        this.maxStrength= section.getMaxStrength();
        this.currentStrength= section.getCurrentStrength();
    }
}