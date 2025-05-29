package com.sipun.UniversityBackend.academic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotDTO {
    private DayOfWeek day;
    private int period;
    private String startTime;
    private String endTime;
    private String shift;
}
