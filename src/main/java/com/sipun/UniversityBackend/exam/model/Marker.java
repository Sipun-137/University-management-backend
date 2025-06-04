package com.sipun.UniversityBackend.exam.model;

import com.sipun.UniversityBackend.faculty.model.Faculty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "markers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", unique = true)
    private Faculty faculty;

    @Column(nullable = false)
    private String fullName;

    private Boolean isModerator = false;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL)
    private List<MarkingAssignment> markingAssignments;
}

