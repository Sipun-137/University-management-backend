package com.sipun.UniversityBackend.academic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference("course-branches")
    private Course course;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Semester> semesters = new ArrayList<>();

    @OneToMany(mappedBy = "branch")
    @Builder.Default
    private List<Section> sections = new ArrayList<>();
}