package com.sipun.UniversityBackend.exam.model;


import com.sipun.UniversityBackend.academic.model.*;
import com.sipun.UniversityBackend.exam.enums.ExamType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamType examType; // MID_SEM, FINAL

    private LocalDate examDate;
    private Double totalMarks;
    private Integer durationMinutes;
    private Boolean isPublished = false;
    private Integer version = 1;

    // Relationships
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamResult> results;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<MarkingAssignment> markingAssignments;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Rubric> rubrics;

    // Derived from relationships
    public Course getCourse() {
        return this.branch.getCourse();
    }
}