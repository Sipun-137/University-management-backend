package com.sipun.UniversityBackend.academic.service;

import com.sipun.UniversityBackend.academic.dto.Shift;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.model.TimeTableEntry;
import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.academic.repo.TimeTableEntryRepo;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import com.sipun.UniversityBackend.faculty.repo.FacultyAssignmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TimeTableService {

    @Autowired
    private SectionRepo sectionRepository;
    @Autowired
    private SubjectRepo subjectRepository;
    @Autowired
    private FacultyAssignmentRepository facultyAssignmentRepository;
    @Autowired
    private TimeTableEntryRepo timeTableEntryRepository;

    private final Map<Shift, List<PeriodSlot>> shiftTimings = Map.of(
            Shift.MORNING, List.of(
                    new PeriodSlot(1, LocalTime.of(8, 0), LocalTime.of(9, 0)),
                    new PeriodSlot(2, LocalTime.of(9, 0), LocalTime.of(10, 0)),
                    new PeriodSlot(3, LocalTime.of(10, 0), LocalTime.of(11, 0))
            ),
            Shift.AFTERNOON, List.of(
                    new PeriodSlot(4, LocalTime.of(13, 0), LocalTime.of(14, 0)),
                    new PeriodSlot(5, LocalTime.of(14, 0), LocalTime.of(15, 0)),
                    new PeriodSlot(6, LocalTime.of(15, 0), LocalTime.of(16, 0))
            )
    );

    private final List<DayOfWeek> weekDays = List.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
    );


    public void generateTimeTable(Long branchId, String academicYear) {


        int startYear = Integer.parseInt(academicYear.split("-")[0]);
        List<Section> sections = sectionRepository.findByBranchIdAndBatchStartYear(branchId, startYear);
        log.info("Generating timetable for branchId={} and academicYear={} (startYear={})", branchId, academicYear, startYear);
        log.info("Found {} sections", sections.size());

        boolean timetableExists = sections.stream().anyMatch(section ->
                timeTableEntryRepository.existsBySectionIdAndAcademicYear(section.getId(), academicYear)
        );

        if (timetableExists) {
            log.warn("Timetable already exists for branchId={} and academicYear={}", branchId, academicYear);
            throw new IllegalStateException("Timetable already generated for this batch and academic year.");
        }

        for (Section section : sections) {
            log.info("Processing Section: {} (ID: {})", section.getName(), section.getId());

//            List<FacultyAssignment> assignments = facultyAssignmentRepository.findBySectionIdAndAcademicYear(section.getId(), academicYear);
//            List<Subject> subjects = assignments.stream()
//                    .map(FacultyAssignment::getSubject)
//                    .distinct()
//                    .collect(Collectors.toList());
//
//            Map<Long, Integer> subjectHoursLeft =
//            log.info("Subjects for section {}: {}", section.getName(), subjects.stream().map(Subject::getName).toList());
//
//
//            List<FacultyAssignment> assignmentsBySection = facultyAssignmentRepository.findBySectionId(section.getId());
//            Map<Long, Faculty> subjectToFaculty = assignmentsBySection.stream()
//                    .collect(Collectors.toMap(a -> a.getSubject().getId(), FacultyAssignment::getFaculty));
//
//            Collections.shuffle(subjects); // Shuffle to spread subjects
//            log.info("Shuffled subjects order for section {}", section.getName());
//
//            int totalExpectedHours = subjectHoursLeft.values().stream().mapToInt(i -> i).sum();
//            int retries = 0;
//            int maxRetries = 3;

            List<FacultyAssignment> assignments = facultyAssignmentRepository.findBySectionIdAndAcademicYear(section.getId(), academicYear);
            log.info("Found {} assignments",assignments.size());
            if (assignments.isEmpty()) {
                log.info("No faculty assignments for section {}", section.getName());
                continue;
            }
            List<Subject> subjects = assignments.stream()
                    .map(FacultyAssignment::getSubject)
                    .distinct()
                    .collect(Collectors.toList());


            Map<Long, Integer> subjectHoursLeft = subjects.stream()
                    .collect(Collectors.toMap(Subject::getId, Subject::getWeeklyHours));
            log.info("Subjects for section {}: {}", section.getName(), subjects.stream().map(Subject::getName).toList());
            List<FacultyAssignment> assignmentsBySection = facultyAssignmentRepository.findBySectionId(section.getId());
            Map<Long, Faculty> subjectToFaculty=assignmentsBySection.stream()
                    .collect(Collectors.toMap(a -> a.getSubject().getId(), FacultyAssignment::getFaculty));

            Collections.shuffle(subjects); // Shuffle to spread subjects
            log.info("Shuffled subjects order for section {}", section.getName());

//            for (FacultyAssignment fa : assignmentsBySection) {
//                Long subjectId = fa.getSubject().getId();
//                log.info(subjectId.toString());
////               subjectToFaculty.put(subjectId, fa.getFaculty());
//                subjectHoursLeft.put(subjectId,
//                        subjectHoursLeft.getOrDefault(subjectId, 0) + fa.getWeeklyHours());
//            }

            int totalExpectedHours = subjectHoursLeft.values().stream().mapToInt(i -> i).sum();
            int retries = 0, maxRetries = 3;

            while (subjectHoursLeft.values().stream().anyMatch(h -> h > 0) && retries < maxRetries) {
                retries++;
                log.info("Retry attempt {} for section {}", retries, section.getName());
                List<DayOfWeek> shuffledDays = new ArrayList<>(weekDays);
                Collections.shuffle(shuffledDays);
                for (DayOfWeek day : shuffledDays) {
                    List<PeriodSlot> allSlots = new ArrayList<>();
                    for (Shift shift : Shift.values()) {
                        allSlots.addAll(shiftTimings.get(shift));
                        Collections.shuffle(allSlots);
                        for (PeriodSlot slot : allSlots) {
                            Optional<Subject> subjectOpt = subjects.stream()
                                    .filter(sub -> subjectHoursLeft.getOrDefault(sub.getId(), 0) > 0)
                                    .sorted(Comparator.comparingInt(sub -> -subjectHoursLeft.get(sub.getId()))) // prioritize by hours left
                                    .filter(sub -> !isSubjectRepeatedOnSameDay(section.getId(), sub.getId(), day))
                                    .findFirst();

                            if (subjectOpt.isEmpty()) {
                                log.info("No subject available for section={}, day={}, shift={}, slot={}", section.getName(), day, shift, slot.getPeriodNumber());
                                continue;
                            }

                            Subject subject = subjectOpt.get();
                            Faculty faculty = subjectToFaculty.get(subject.getId());

                            if (faculty == null) {
                                log.info("No faculty assigned for subject={} in section={}", subject.getName(), section.getName());
                                continue;
                            }

                            if (hasFacultyConflict(faculty.getId(), day, slot)) {
                                log.info("Faculty conflict for {} at {} period {} on {}", faculty.getName(), shift, slot.getPeriodNumber(), day);
                                continue;
                            }
                            if (hasSectionConflict(section.getId(), day, slot)) {
                                log.info("Section conflict for {} at {} period {} on {}", section.getName(), shift, slot.getPeriodNumber(), day);
                                continue;
                            }
                            TimeTableEntry entry = TimeTableEntry.builder()
                                    .section(section)
                                    .subject(subject)
                                    .faculty(faculty)
                                    .day(day)
                                    .startTime(slot.getStartTime())
                                    .endTime(slot.getEndTime())
                                    .period(slot.getPeriodNumber())
                                    .shift(shift)
                                    .semester(subject.getSemester())
                                    .academicYear(academicYear)
                                    .validFrom(LocalDate.now())
                                    .validUntil(LocalDate.now().plusMonths(6))
                                    .isRecurring(true)
                                    .build();

                            timeTableEntryRepository.save(entry);
                            subjectHoursLeft.put(subject.getId(), subjectHoursLeft.get(subject.getId()) - 1);

                            log.info("Scheduled: Section={}, Subject={}, Faculty={}, Day={}, Shift={}, Period={}",
                                    section.getName(), subject.getName(), faculty.getName(), day, shift, slot.getPeriodNumber());
                        }
                    }
                }
            }

            int totalScheduled = subjectHoursLeft.entrySet().stream()
                    .mapToInt(e -> subjectRepository.findById(e.getKey()).get().getWeeklyHours() - e.getValue())
                    .sum();

            log.info("📘 Report for Section {}: Scheduled = {} hrs, Expected = {} hrs, Missed = {} hrs",
                    section.getName(), totalScheduled, totalExpectedHours, totalExpectedHours - totalScheduled);

            if (totalScheduled < totalExpectedHours) {
                log.info("❌ Incomplete timetable for section {}. Missing {} hours",
                        section.getName(), totalExpectedHours - totalScheduled);
            } else {
                log.info("✅ Complete timetable generated for section {}", section.getName());
            }


//            for (DayOfWeek day : weekDays) {
//                for (Shift shift : Shift.values()) {
//                    for (PeriodSlot slot : shiftTimings.get(shift)) {
//                        Optional<Subject> subjectOpt = subjects.stream()
//                                .filter(sub -> subjectHoursLeft.get(sub.getId()) > 0)
//                                .filter(sub -> !isSubjectRepeatedOnSameDay(section.getId(), sub.getId(), day))
//                                .findFirst();
//
//
//                        if (subjectOpt.isEmpty()) {
//                            log.info("No subject available for section={}, day={}, shift={}, slot={}", section.getName(), day, shift, slot.getPeriodNumber());
//                            continue;
//                        };
//
//                        Subject subject = subjectOpt.get();
//                        Faculty faculty = subjectToFaculty.get(subject.getId());
//
//                        if (faculty == null) {
//                            log.info("No faculty assigned for subject={} in section={}", subject.getName(), section.getName());
//                            continue;
//                        }
//
//                        if (faculty == null) {
//                            log.info("No faculty assigned for subject {} in section {}", subject.getName(), section.getName());
//                            continue;
//                        }
//
//                        // Check for conflicts
//                        if (hasFacultyConflict(faculty.getId(), day, slot)) {
//                            log.info("Faculty conflict for {} at {} period {} on {}", faculty.getName(), shift, slot.getPeriodNumber(), day);
//                            continue;
//                        }
//
//                        if (hasSectionConflict(section.getId(), day, slot)) {
//                            log.info("Section conflict for {} at {} period {} on {}", section.getName(), shift, slot.getPeriodNumber(), day);
//                            continue;
//                        }
//
//                        TimeTableEntry entry = TimeTableEntry.builder()
//                                .section(section)
//                                .subject(subject)
//                                .faculty(faculty)
//                                .day(day)
//                                .startTime(slot.getStartTime())
//                                .endTime(slot.getEndTime())
//                                .period(slot.getPeriodNumber())
//                                .shift(shift)
//                                .semester(subject.getSemester())
//                                .academicYear(academicYear)
//                                .validFrom(LocalDate.now())
//                                .validUntil(LocalDate.now().plusMonths(6))
//                                .isRecurring(true)
//                                .build();
//
//
//                        timeTableEntryRepository.save(entry);
//                        log.info("Saved timetable entry: section={}, subject={}, faculty={}, day={}, slot={}",
//                                section.getName(), subject.getName(), faculty.getName(), day, slot.getPeriodNumber());
//
//                        subjectHoursLeft.put(subject.getId(), subjectHoursLeft.get(subject.getId()) - 1);
//
//                        log.info("Scheduled: Section={}, Subject={}, Faculty={}, Day={}, Shift={}, Period={}",
//                                section.getName(), subject.getName(), faculty.getName(), day, shift, slot.getPeriodNumber());
//                    }
//                }
//            }
        }

        log.info("Timetable generation completed for academicYear={} and branchId={}", academicYear, branchId);
    }

    private boolean hasFacultyConflict(Long facultyId, DayOfWeek day, PeriodSlot slot) {
        return !timeTableEntryRepository.findFacultyConflicts(facultyId, day, slot.getStartTime(), slot.getEndTime()).isEmpty();
    }

    private boolean hasSectionConflict(Long sectionId, DayOfWeek day, PeriodSlot slot) {
        return !timeTableEntryRepository.findSectionConflicts(sectionId, day, slot.getStartTime(), slot.getEndTime()).isEmpty();
    }

    private boolean isSubjectRepeatedOnSameDay(Long sectionId, Long subjectId, DayOfWeek day) {
        return !timeTableEntryRepository.findBySectionIdAndSubjectIdAndDay(sectionId, subjectId, day).isEmpty();
    }

    @Data
    @AllArgsConstructor
    static class PeriodSlot {
        private int periodNumber;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
