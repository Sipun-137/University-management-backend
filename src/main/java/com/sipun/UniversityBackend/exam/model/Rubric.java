package com.sipun.UniversityBackend.exam.model;

import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.exam.enums.RubricStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rubrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rubric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    private Integer questionNumber;

    @Lob
    private String criteria;

    private Double maxMarks;

    @Enumerated(EnumType.STRING)
    private RubricStatus status = RubricStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;  //basically the exam controller

    @Version
    private Integer version;

}
