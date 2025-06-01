package com.sipun.UniversityBackend.exam.model;

import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Semester;
import com.sipun.UniversityBackend.academic.model.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private com.sipun.UniversityBackend.student.model.Student student;

    private Double marksObtained;
    private Boolean isAbsent = false;

    @Column(length = 1000)
    private String moderationNotes;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    private String anonymizedId; // For blind marking

    // Derived properties
    public Subject getSubject() {
        return this.exam.getSubject();
    }

    public Semester getSemester() {
        return this.exam.getSemester();
    }

    public Batch getBatch() {
        return this.exam.getBatch();
    }

    public Branch getBranch() {
        return this.exam.getBranch();
    }
}