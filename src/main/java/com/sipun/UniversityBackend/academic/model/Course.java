package com.sipun.UniversityBackend.academic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id" // Use the "id" field to resolve circular references
//)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "B.Tech", "BCA"

    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private int durationInYears;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference("course-batches")
    private List<Batch> batches;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference("course-branches")
    private List<Branch> branches;
}