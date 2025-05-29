package com.sipun.UniversityBackend.academic.service;


import com.sipun.UniversityBackend.academic.dto.AdminTimeTableDTO;
import com.sipun.UniversityBackend.academic.dto.FacultyTimeTableDTO;
import com.sipun.UniversityBackend.academic.dto.SectionTimeTableDTO;
import com.sipun.UniversityBackend.academic.mapper.TimeTableMapper;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.TimeTableEntry;
import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import com.sipun.UniversityBackend.academic.repo.TimeTableEntryRepo;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimeTableQueryService {

    @Autowired
    private TimeTableEntryRepo timeTableEntryRepo;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private SectionRepo sectionRepo;

    /**
     * For Faculty to view their timetable
     */
    public List<FacultyTimeTableDTO> getTimeTableForFaculty(Long facultyId) {
        List<TimeTableEntry> entries =timeTableEntryRepo.findByFacultyIdOrderByDayAscStartTimeAsc(facultyId);
        List<FacultyTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toFacultyTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * For Student to view timetable of their section
     */
    public List<SectionTimeTableDTO> getTimeTableForSection(Long sectionId) {
        List<TimeTableEntry> entries =timeTableEntryRepo.findBySectionIdOrderByDayAscStartTimeAsc(sectionId);
        List<SectionTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toSectionTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * For Admin to view all timetables
     */
    public List<AdminTimeTableDTO>  getAllTimeTables() {
        List<TimeTableEntry> entries = timeTableEntryRepo.findAllByOrderBySectionNameAscDayAscStartTimeAsc();
        List<AdminTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toAdminTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * For Admin to filter by branch and academic year
     */
    public List<AdminTimeTableDTO> getTimeTableByBranchAndAcademicYear(Long branchId, String academicYear) {
        List<TimeTableEntry> entries=timeTableEntryRepo.findBySectionBranchIdAndAcademicYearOrderBySectionNameAscDayAscStartTimeAsc(branchId, academicYear);
        List<AdminTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toAdminTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * For Admin to filter by section and academic year
     */
    public List<SectionTimeTableDTO> getTimeTableBySectionAndAcademicYear(Long sectionId, String academicYear) {
        List<TimeTableEntry> entries =timeTableEntryRepo.findBySectionIdAndAcademicYearOrderByDayAscStartTimeAsc(sectionId, academicYear);
        List<SectionTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toSectionTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * Filter timetable by day for a faculty
     */
    public List<FacultyTimeTableDTO> getTimeTableForFacultyByDay(Long facultyId, DayOfWeek day) {

        List<TimeTableEntry> entries =timeTableEntryRepo.findByFacultyIdAndDayOrderByStartTimeAsc(facultyId, day);
        List<FacultyTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toFacultyTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * Filter timetable by day for a section
     */
    public List<SectionTimeTableDTO> getTimeTableForSectionByDay(Long sectionId, DayOfWeek day) {
        List<TimeTableEntry> entries =timeTableEntryRepo.findBySectionIdAndDayOrderByStartTimeAsc(sectionId, day);
        List<SectionTimeTableDTO> dtoList = entries.stream()
                .map(TimeTableMapper::toSectionTimeTableDTO)
                .collect(Collectors.toList());
        return dtoList;
    }
}

