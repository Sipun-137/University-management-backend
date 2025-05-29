package com.sipun.UniversityBackend.academic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int startYear;
    private int endYear;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference("course-branches")
    private Course course;


    @OneToMany(mappedBy = "batch")
    @JsonManagedReference("batch-sections") // Serialize this side
    private List<Section> sections;
}
