package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchMinimalDTO {
    private Long id;
    private int startYear;
    private int endYear;

    public BatchMinimalDTO(Batch batch){
        this.id= batch.getId();
        this.startYear= batch.getStartYear();
        this.endYear= batch.getEndYear();
    }
}
