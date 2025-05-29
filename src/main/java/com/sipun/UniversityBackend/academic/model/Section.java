package com.sipun.UniversityBackend.academic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int maxStrength = 60; // Default class size

    @Column(nullable = false)
    private int currentStrength = 0;


    @ToString.Exclude // Break Lombok toString() loop
    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    @JsonBackReference("batch-sections") // Break JSON loop
    private Batch batch;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonBackReference("branch-sections")
    private Branch branch;

    @ManyToMany
    @JoinTable(
            name = "section_subjects",
            joinColumns = {@JoinColumn(name = "section_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "section")
    @JsonManagedReference("section-timetable")
    private List<TimeTableEntry> timeTableEntries;

    public boolean hasAvailableSeats() {
        return currentStrength < maxStrength;
    }
}