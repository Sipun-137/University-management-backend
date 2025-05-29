package com.sipun.UniversityBackend.faculty.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FacultyAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonBackReference
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private int weeklyHours;
}
