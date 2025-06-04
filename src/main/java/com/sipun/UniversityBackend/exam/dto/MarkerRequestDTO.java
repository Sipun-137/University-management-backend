package com.sipun.UniversityBackend.exam.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkerRequestDTO {
    private Long staffId;
    private Boolean isModerator;
}

