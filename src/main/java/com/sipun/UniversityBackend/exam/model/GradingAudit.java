package com.sipun.UniversityBackend.exam.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "grading_audits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradingAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private ExamResult examResult;

    private Double oldMarks;
    private Double newMarks;

    @Column(nullable = false)
    private Long changedBy; // Staff ID

    @Lob
    private String changeReason;

    @CreationTimestamp
    private LocalDateTime changedAt;
}