package com.sipun.UniversityBackend.grievance.model;

import com.sipun.UniversityBackend.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grievance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private GrievanceCategory category;

    @Enumerated(EnumType.STRING)
    private GrievanceStatus status = GrievanceStatus.PENDING;

    private String response; // Admin's remarks

    private String attachmentUrl;

    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private User submittedBy;
}
