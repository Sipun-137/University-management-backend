package com.sipun.UniversityBackend.attendance.service;


import com.sipun.UniversityBackend.academic.dto.SectionMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.TimeSlotDTO;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.*;

import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import com.sipun.UniversityBackend.academic.repo.SemesterRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.academic.repo.TimeTableEntryRepo;
import com.sipun.UniversityBackend.attendance.dto.AttendanceRequest;
import com.sipun.UniversityBackend.attendance.dto.FacultyScheduleDTO;
import com.sipun.UniversityBackend.attendance.dto.StudentAttendanceDTO;
import com.sipun.UniversityBackend.attendance.model.AttendanceRecord;
import com.sipun.UniversityBackend.attendance.model.AttendanceSession;
import com.sipun.UniversityBackend.attendance.model.AttendanceStatus;
import com.sipun.UniversityBackend.attendance.repo.AttendanceRecordRepository;
import com.sipun.UniversityBackend.attendance.repo.AttendanceSessionRepository;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import com.sipun.UniversityBackend.student.model.Student;
import com.sipun.UniversityBackend.student.repo.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AttendanceService {

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private TimeTableEntryRepo ttRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private SectionRepo sectionRepo;
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private FacultyRepo facultyRepo;
    @Autowired
    private SemesterRepo semRepo;

    @Autowired
    private TimeTableEntryRepo timeTableEntryRepo;

    // MARK ATTENDANCE
    public void markAttendance(AttendanceRequest request) {
        boolean classExists = ttRepo.existsBySectionIdAndSubjectIdAndFacultyIdAndSemesterIdAndPeriod(request.getSectionId(), request.getSubjectId(), request.getFacultyId(), request.getSemesterId(), request.getPeriod());
        log.info(classExists ? "Attendance marked successfully" : "Attendance marked failed");
        if (!classExists) {
            throw new ResourceNotFoundException("No such classes Available for this entry");
        }

        boolean exists = attendanceSessionRepository.existsBySectionIdAndSubjectIdAndDateAndPeriodAndShift(
                request.getSectionId(), request.getSubjectId(), request.getDate(), request.getPeriod(), request.getShift());

        if (exists) {
            throw new IllegalStateException("Attendance already marked for this session.");
        }


        Section section = sectionRepo.findById(request.getSectionId()).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Subject subject = subjectRepo.findById(request.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject Not Found"));
        Faculty faculty = facultyRepo.findById(request.getFacultyId()).orElseThrow(() -> new ResourceNotFoundException("Faculty Not Found"));
        Semester semester = semRepo.findById(request.getSemesterId()).orElseThrow(() -> new ResourceNotFoundException("Semester Not Found"));

        // Build session
        AttendanceSession session = AttendanceSession.builder()
                .section(section)
                .subject(subject)
                .faculty(faculty)
                .semester(semester)
                .date(request.getDate())
                .day(request.getDate().getDayOfWeek())
                .period(request.getPeriod())
                .shift(request.getShift())
                .build();

        attendanceSessionRepository.save(session);

        // Save attendance records
        List<AttendanceRecord> records = request.getStudentAttendance().stream().map(sa -> {
            Student student = studentRepo.findById(sa.getStudentId())
                    .orElseThrow(() -> new NoSuchElementException("Student not found: " + sa.getStudentId()));
            return AttendanceRecord.builder()
                    .attendanceSession(session)
                    .student(student)
                    .status(sa.getStatus())
                    .remarks(sa.getRemarks())
                    .build();
        }).collect(Collectors.toList());

        attendanceRecordRepository.saveAll(records);
        log.info("Marked attendance for section {}, subject {}, date {}", request.getSectionId(), request.getSubjectId(), request.getDate());
    }

    // STUDENT SEMESTER-WISE ATTENDANCE
    public List<StudentAttendanceDTO> getStudentAttendanceForSemester(Long studentId, Long semesterId) {
        List<AttendanceRecord> records = attendanceRecordRepository
                .findByStudentIdAndAttendanceSession_SemesterId(studentId, semesterId);

        Map<Subject, List<AttendanceRecord>> grouped = records.stream()
                .collect(Collectors.groupingBy(r -> r.getAttendanceSession().getSubject()));

        List<StudentAttendanceDTO> response = new ArrayList<>();
        for (Map.Entry<Subject, List<AttendanceRecord>> entry : grouped.entrySet()) {
            Subject subject = entry.getKey();
            List<AttendanceRecord> subjectRecords = entry.getValue();

            long present = subjectRecords.stream().filter(r -> r.getStatus() == AttendanceStatus.PRESENT).count();
            long total = subjectRecords.size();

            response.add(new StudentAttendanceDTO(
                    subject.getName(),
                    subject.getSubjectCode(),
                    (int) total,
                    (int) present,
                    (int) (total - present),
                    total == 0 ? 0 : (present * 100.0) / total
            ));
        }
        return response;
    }

    public List<FacultyScheduleDTO> getFacultyClassesToday(Long facultyId) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        List<TimeTableEntry> entries =timeTableEntryRepo.findByFacultyIdAndDayOrderByStartTimeAsc(facultyId, dayOfWeek);

        return entries.stream()
                .map(entry -> {
                    boolean exists = attendanceSessionRepository.existsBySectionIdAndSubjectIdAndDateAndPeriodAndShift(entry.getSection().getId(), entry.getSubject().getId(), today, entry.getPeriod(), entry.getShift());
                    return FacultyScheduleDTO.builder()
                            .attendanceTaken(exists)
                            .section(new SectionMinimalDTO(entry.getSection()))
                            .semester(new SemesterMinimalDTO(entry.getSemester()))
                            .subject(new SubjectMinimalDTO(entry.getSubject()))
                            .timeSlot(TimeSlotDTO.builder()
                                    .day(entry.getDay())
                                    .endTime(entry.getEndTime().toString())
                                    .period(entry.getPeriod())
                                    .startTime(entry.getStartTime().toString())
                                    .shift(entry.getShift().name())
                                    .build())
                            .build();
                }).collect(Collectors.toList());

    }
}
