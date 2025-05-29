package com.sipun.UniversityBackend.faculty.service;

import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import com.sipun.UniversityBackend.faculty.repo.FacultyAssignmentRepository;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyAssignmentService {

    @Autowired
    private SectionRepo sectionRepository;
    @Autowired
    private SubjectRepo subjectRepository;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private FacultyAssignmentRepository facultyAssignmentRepository;

    @Transactional
    public void assignFacultyToSubjects(Long semesterNumber, Long batchId, String academicYear,String term) {

        // Step 1: Fetch sections for the given batch
        List<Section> sections = sectionRepository.findByBatchId(batchId);
        if (sections.isEmpty()) {
            throw new RuntimeException("No sections found for the batch ID: " + batchId);
        }

        // Step 2: Fetch subjects for the given semester
        List<Subject> subjects = subjectRepository.findBySemesterNumber(semesterNumber);
        if (subjects.isEmpty()) {
            throw new RuntimeException("No subjects found for semester: " + semesterNumber);
        }

        // Step 3: Fetch all faculties with teachable subjects
        List<Faculty> faculties = facultyRepository.findAllWithTeachableSubjects();

        // Step 4: Calculate current weekly load of faculties
        Map<Long, Integer> facultyLoadMap = calculateFacultyLoads(faculties, academicYear);

        for (Section section : sections) {
            for (Subject subject : subjects) {

                // Filter eligible faculties
                List<Faculty> eligibleFaculties = faculties.stream()
                        .filter(f -> f.getTeachableSubjects().contains(subject))
                        .filter(f -> facultyLoadMap.getOrDefault(f.getId(), 0) + subject.getWeeklyHours() <= f.getMaxLoadPerWeek())
                        .sorted(Comparator.comparingInt(f -> facultyLoadMap.getOrDefault(f.getId(), 0)))
                        .collect(Collectors.toList());

                if (eligibleFaculties.isEmpty()) {
                    throw new RuntimeException("No eligible faculty found for subject: " + subject.getName());
                }

                Faculty selectedFaculty = eligibleFaculties.get(0);

                // Create and save assignment
                FacultyAssignment assignment = new FacultyAssignment();
                assignment.setFaculty(selectedFaculty);
                assignment.setSubject(subject);
                assignment.setSection(section);
                assignment.setAcademicYear(academicYear);
                assignment.setTerm(term);

                facultyAssignmentRepository.save(assignment);

                // Update the load map
                int updatedLoad = facultyLoadMap.getOrDefault(selectedFaculty.getId(), 0) + subject.getWeeklyHours();
                facultyLoadMap.put(selectedFaculty.getId(), updatedLoad);
            }
        }
    }

    private Map<Long, Integer> calculateFacultyLoads(List<Faculty> faculties, String academicYear) {
        List<FacultyAssignment> currentAssignments = facultyAssignmentRepository.findByAcademicYear(academicYear);

        Map<Long, Integer> loadMap = new HashMap<>();
        for (FacultyAssignment assignment : currentAssignments) {
            Long facultyId = assignment.getFaculty().getId();
            int hours = assignment.getSubject().getWeeklyHours();
            loadMap.put(facultyId, loadMap.getOrDefault(facultyId, 0) + hours);
        }
        return loadMap;
    }



}

