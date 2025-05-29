package com.sipun.UniversityBackend.academic.dto;

import com.sipun.UniversityBackend.academic.dto.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class TimeTableEntryDTO {
    private String subjectName;
    private String subjectCode;
    private String facultyName;
    private String sectionName;
    private int semesterNumber;
    private DayOfWeek day;
    private Shift shift;
    private int period;
    private LocalTime startTime;
    private LocalTime endTime;
}
