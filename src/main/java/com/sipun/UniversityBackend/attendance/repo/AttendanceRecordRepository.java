package com.sipun.UniversityBackend.attendance.repo;

import com.sipun.UniversityBackend.attendance.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRecordRepository  extends JpaRepository<AttendanceRecord,Long> {
    List<AttendanceRecord> findByStudentIdAndAttendanceSession_SemesterId(Long studentId, Long semesterId);
    List<AttendanceRecord> findByAttendanceSession_Id(Long sessionId);
    List<AttendanceRecord> findByStudent_Id(Long studentId);
}

