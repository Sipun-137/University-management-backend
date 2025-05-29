package com.sipun.UniversityBackend.attendance.service;

import com.sipun.UniversityBackend.attendance.dto.AttendanceRecordDTO;
import com.sipun.UniversityBackend.attendance.dto.AttendanceSessionDTO;
import com.sipun.UniversityBackend.attendance.dto.StudentAttendanceSummaryDTO;
import com.sipun.UniversityBackend.attendance.model.AttendanceRecord;
import com.sipun.UniversityBackend.attendance.model.AttendanceSession;
import com.sipun.UniversityBackend.attendance.model.AttendanceStatus;
import com.sipun.UniversityBackend.attendance.repo.AttendanceRecordRepository;
import com.sipun.UniversityBackend.attendance.repo.AttendanceSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAttendanceService {

    private final AttendanceSessionRepository sessionRepository;
    private final AttendanceRecordRepository recordRepository;

    // 1. Fetch all sessions (optional filtering)
    public List<AttendanceSessionDTO> getSessions(int startYear, Long branchId) {
        return sessionRepository.findBySection_Batch_StartYearAndSection_Branch_Id(startYear, branchId)
                .stream()
                .map(this::mapToSessionDTO)
                .toList();
    }

    // 2. Fetch attendance records of a session
    public List<AttendanceRecordDTO> getRecordsForSession(Long sessionId) {
        return recordRepository.findByAttendanceSession_Id(sessionId)
                .stream()
                .map(this::mapToRecordDTO)
                .toList();
    }

    // 3. Get student summary
    public StudentAttendanceSummaryDTO getStudentAttendanceSummary(Long studentId) {
        List<AttendanceRecord> records = recordRepository.findByStudent_Id(studentId);
        long total = records.size();
        long present = records.stream().filter(r -> r.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = records.stream().filter(r -> r.getStatus() == AttendanceStatus.ABSENT).count();
        long late = records.stream().filter(r -> r.getStatus() == AttendanceStatus.LATE).count();
        long excuse = records.stream().filter(r -> r.getStatus() == AttendanceStatus.EXCUSE).count();

        double percentage = total == 0 ? 0 : (present + late * 0.5 + excuse * 0.5) / total * 100;

        return StudentAttendanceSummaryDTO.builder()
                .studentId(studentId)
                .studentName(records.get(0).getStudent().getName())
                .totalSessions((int) total)
                .present((int) present)
                .absent((int) absent)
                .late((int) late)
                .excuse((int) excuse)
                .attendancePercentage(percentage)
                .build();
    }

    // 4. Update attendance
    public void updateAttendanceStatus(Long recordId, AttendanceStatus newStatus, String remarks) {
        AttendanceRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("AttendanceRecord not found"));
        record.setStatus(newStatus);
        record.setRemarks(remarks);
        recordRepository.save(record);
    }

    // Mappers
    private AttendanceSessionDTO mapToSessionDTO(AttendanceSession session) {
        return AttendanceSessionDTO.builder()
                .id(session.getId())
                .date(session.getDate())
                .subjectName(session.getSubject().getName())
                .facultyName(session.getFaculty().getName())
                .sectionName(session.getSection().getName())
                .shift(session.getShift())
                .period(session.getPeriod())
                .build();
    }

    private AttendanceRecordDTO mapToRecordDTO(AttendanceRecord record) {
        return AttendanceRecordDTO.builder()
                .studentId(record.getStudent().getId())
                .studentName(record.getStudent().getName())
                .status(record.getStatus())
                .remarks(record.getRemarks())
                .build();
    }
}
