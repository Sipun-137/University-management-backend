package com.sipun.UniversityBackend.grievance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrievanceStatsDTO {
    private long total;
    private Map<String, Long> statusCounts;
    private Map<String, Long> categoryCounts;
}
