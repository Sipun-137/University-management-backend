package com.sipun.UniversityBackend.notice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Lob
    private String content;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    private LocalDate validFrom;
    private LocalDate validTo;

    private String targetAudience;

    private LocalDateTime createdAt;

}
