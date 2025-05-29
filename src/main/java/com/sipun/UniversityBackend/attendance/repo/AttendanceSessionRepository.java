package com.sipun.UniversityBackend.attendance.repo;

import com.sipun.UniversityBackend.academic.dto.Shift;
import com.sipun.UniversityBackend.attendance.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    boolean existsBySectionIdAndSubjectIdAndDateAndPeriodAndShift(Long sectionId, Long subjectId, LocalDate date, int period, Shift shift);

    List<AttendanceSession> findBySection_Batch_StartYearAndSection_Branch_Id(int startYear, Long branchId);
}
