package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.dto.Shift;
import com.sipun.UniversityBackend.academic.model.TimeTableEntry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeTableEntryRepo extends JpaRepository<TimeTableEntry, Long> {



    List<TimeTableEntry> findBySectionId(Long sectionId);

    // 2. Get entries for a section and day
    List<TimeTableEntry> findBySectionIdAndDay(Long sectionId, DayOfWeek day);

    // 3. Get entries for a faculty on a specific day
    List<TimeTableEntry> findByFacultyIdAndDay(Long facultyId, DayOfWeek day);

    // 4. Get entries for a section and specific subject on a day
    List<TimeTableEntry> findBySectionIdAndSubjectIdAndDay(Long sectionId, Long subjectId, DayOfWeek day);

    // 5. Check faculty time conflict (overlapping slot)
    @Query("""
        SELECT t FROM TimeTableEntry t 
        WHERE t.faculty.id = :facultyId 
          AND t.day = :day 
          AND (
            (t.startTime <= :startTime AND t.endTime > :startTime) OR
            (t.startTime < :endTime AND t.endTime >= :endTime) OR
            (t.startTime >= :startTime AND t.endTime <= :endTime)
          )
    """)
    List<TimeTableEntry> findFacultyConflicts(
            @Param("facultyId") Long facultyId,
            @Param("day") DayOfWeek day,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // 6. Check section time conflict
    @Query("""
        SELECT t FROM TimeTableEntry t 
        WHERE t.section.id = :sectionId 
          AND t.day = :day 
          AND (
            (t.startTime <= :startTime AND t.endTime > :startTime) OR
            (t.startTime < :endTime AND t.endTime >= :endTime) OR
            (t.startTime >= :startTime AND t.endTime <= :endTime)
          )
    """)
    List<TimeTableEntry> findSectionConflicts(
            @Param("sectionId") Long sectionId,
            @Param("day") DayOfWeek day,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // 7. Get entries for a given section and shift
    List<TimeTableEntry> findBySectionIdAndShift(Long sectionId, Shift shift);

    // 8. Delete all entries for an academic year and branch (for regeneration)
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM TimeTableEntry t 
        WHERE t.academicYear = :academicYear 
          AND t.section.branch = :branch
    """)
    void deleteByAcademicYearAndBranch(
            @Param("academicYear") String academicYear,
            @Param("branch") String branch
    );

    // 9. Get timetable for a faculty across all sections
    List<TimeTableEntry> findByFacultyId(Long facultyId);

    // 10. Get all entries for a section on a specific shift and day
    List<TimeTableEntry> findBySectionIdAndDayAndShift(Long sectionId, DayOfWeek day, Shift shift);

    List<TimeTableEntry> findByFacultyIdOrderByDayAscStartTimeAsc(Long facultyId);

    List<TimeTableEntry> findBySectionIdOrderByDayAscStartTimeAsc(Long sectionId);

    List<TimeTableEntry> findAllByOrderBySectionNameAscDayAscStartTimeAsc();

    List<TimeTableEntry> findBySectionBranchIdAndAcademicYearOrderBySectionNameAscDayAscStartTimeAsc(Long branchId, String academicYear);

    List<TimeTableEntry> findBySectionIdAndAcademicYearOrderByDayAscStartTimeAsc(Long sectionId, String academicYear);

    List<TimeTableEntry> findByFacultyIdAndDayOrderByStartTimeAsc(Long facultyId, DayOfWeek day);

    List<TimeTableEntry> findBySectionIdAndDayOrderByStartTimeAsc(Long sectionId, DayOfWeek day);

    boolean existsBySectionIdAndAcademicYear(Long sectionId, String academicYear);

    boolean existsBySectionIdAndSubjectIdAndFacultyIdAndSemesterIdAndPeriod(Long sectionId,Long subjectId,Long facultyId,Long semesterId,int period );

}
