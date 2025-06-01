package com.sipun.UniversityBackend.exam.model;

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

    @Column(unique = true, nullable = false)
    private String staffId;

    @Column(nullable = false)
    private String fullName;

    private Boolean isModerator = false;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL)
    private List<MarkingAssignment> markingAssignments;
}

