package com.sipun.UniversityBackend.academic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "semesters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number; // 1-8

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "semester",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonBackReference
    private List<Subject> subjects=new ArrayList<>();

    @Column(name = "is_current", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isCurrent; // Marks if the semester is ongoing


    @OneToMany(mappedBy = "semester")
    @JsonManagedReference("semester-timetable")
    private List<TimeTableEntry> timeTableEntries;
}