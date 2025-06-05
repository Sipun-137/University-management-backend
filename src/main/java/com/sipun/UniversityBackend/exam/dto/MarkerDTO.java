package com.sipun.UniversityBackend.exam.dto;

import com.sipun.UniversityBackend.exam.model.Marker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDTO {
    private Long id;
    private String fullName;
    private boolean isModerator;


    public MarkerDTO(Marker marker) {
        this.id = marker.getId();
        this.fullName = marker.getFullName();
        this.isModerator = marker.getIsModerator();
    }
}
