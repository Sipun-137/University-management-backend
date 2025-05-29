package com.sipun.UniversityBackend.academic.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int credits;

    @Column(nullable = false, unique = true)
    private String subjectCode;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    @JsonManagedReference
//    @JsonBackReference("semester-subjects")
    private Semester semester;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacultyAssignment> facultyAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    private List<TimeTableEntry> timeTableEntries;

    @Column(nullable = false)
    private int weeklyHours;
}