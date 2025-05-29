package com.sipun.UniversityBackend.academic.mapper;

import com.sipun.UniversityBackend.academic.dto.*;
import com.sipun.UniversityBackend.academic.model.TimeTableEntry;
import com.sipun.UniversityBackend.faculty.dto.FacultyDTO;
import com.sipun.UniversityBackend.faculty.model.Faculty;

public class TimeTableMapper {

    public static SubjectMinimalDTO toSubjectDTO(TimeTableEntry entry) {
        return new SubjectMinimalDTO(
                entry.getSubject().getId(),
                entry.getSubject().getName(),
                entry.getSubject().getSubjectCode(),
                entry.getSubject().getWeeklyHours()
        );
    }

    public static FacultyDTO toFacultyDTO(Faculty faculty) {
        return new FacultyDTO(
                faculty.getId(),
                faculty.getName(),
                faculty.getEmail()
        );
    }

    public static TimeSlotDTO toTimeSlotDTO(TimeTableEntry entry) {
        return new TimeSlotDTO(
                entry.getDay(),
                entry.getPeriod(),
                entry.getStartTime().toString(),
                entry.getEndTime().toString(),
                entry.getShift().name()
        );
    }

    public static FacultyTimeTableDTO toFacultyTimeTableDTO(TimeTableEntry entry) {
        return new FacultyTimeTableDTO(
                toSubjectDTO(entry),
                new SectionMinimalDTO(entry.getSection()),
                entry.getSection().getBranch().getName(),
                new SemesterMinimalDTO(entry.getSemester()),
                toTimeSlotDTO(entry)
        );
    }

    public static SectionTimeTableDTO toSectionTimeTableDTO(TimeTableEntry entry) {
        return new SectionTimeTableDTO(
                toSubjectDTO(entry),
                toFacultyDTO(entry.getFaculty()),
                toTimeSlotDTO(entry)
        );
    }

    public static AdminTimeTableDTO toAdminTimeTableDTO(TimeTableEntry entry) {
        return new AdminTimeTableDTO(
                entry.getSection().getName(),
                entry.getSection().getBranch().getName(),
                toSubjectDTO(entry),
                toFacultyDTO(entry.getFaculty()),
                toTimeSlotDTO(entry),
                entry.getAcademicYear()
        );
    }
}