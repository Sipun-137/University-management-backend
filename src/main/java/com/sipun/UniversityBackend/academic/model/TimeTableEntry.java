package com.sipun.UniversityBackend.academic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sipun.UniversityBackend.academic.dto.Shift;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "time_table_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @JsonBackReference("section-timetable")
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference("subject-timetable")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonBackReference("faculty-timetable")
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    @JsonBackReference("semester-timetable")
    private Semester semester;

    // Day and Time
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift")
    private Shift shift; // MORNING / AFTERNOON (optional but useful)

    @Column(name = "period")
    private Integer period; // 1 to 6 (optional, based on your logic)

    // Date Range
    private LocalDate validFrom;
    private LocalDate validUntil;

    // Location
    private String building;
    private String roomNumber;

    @Column(nullable = false)
    private String academicYear;

    @Column(name = "is_recurring", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isRecurring;

    public boolean isActiveOn(LocalDate date) {
        if (!isRecurring) {
            return date.isEqual(validFrom);
        }
        return !date.isBefore(validFrom) && !date.isAfter(validUntil);
    }
}



