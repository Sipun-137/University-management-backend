package com.sipun.UniversityBackend.notice.dto;

import com.sipun.UniversityBackend.notice.model.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NoticeRequestDTO {
    private String title;
    private String description;
    private String attachmentUrl;
    private NoticeType type;
    private String targetAudience;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String createdBy;
}

