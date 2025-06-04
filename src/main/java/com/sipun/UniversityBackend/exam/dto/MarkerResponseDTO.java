package com.sipun.UniversityBackend.exam.dto;

import com.sipun.UniversityBackend.exam.model.Marker;
import com.sipun.UniversityBackend.exam.model.MarkingAssignment;
import com.sipun.UniversityBackend.faculty.dto.FacultyResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerResponseDTO {
    private Long id;
    private String fullName;
    private FacultyResponseDTO faculty;
    private Boolean isModerator;
    private List<MarkingAssignment> markingAssignments;

    public MarkerResponseDTO(Marker marker) {
        this.id = marker.getId();
        this.fullName = marker.getFullName();
        this.faculty = new FacultyResponseDTO(marker.getFaculty());
        this.isModerator = marker.getIsModerator();
        this.markingAssignments = marker.getMarkingAssignments();
    }
}
