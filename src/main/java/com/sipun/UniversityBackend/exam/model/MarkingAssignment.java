package com.sipun.UniversityBackend.exam.model;

import com.sipun.UniversityBackend.exam.enums.MarkingProgress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marking_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkingAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marker_id", nullable = false)
    private Marker marker;


    @Enumerated(EnumType.STRING)
    private MarkingProgress markingProgress = MarkingProgress.NOT_STARTED;
}