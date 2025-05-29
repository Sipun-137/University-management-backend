package com.sipun.UniversityBackend.faculty.repo;

import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyAssignmentRepository extends JpaRepository<FacultyAssignment, Long> {
    List<FacultyAssignment> findByFaculty_Id(Long facultyId);

    boolean existsByFacultyAndSectionAndSubjectAndAcademicYear(
            Faculty faculty,
            Section section,
            Subject subject,
            String academicYear
    );
    List<FacultyAssignment> findBySubjectAndAcademicYear(Subject subject, String academicYear);


    List<FacultyAssignment> findByAcademicYear(String academicYear);


    List<FacultyAssignment> findBySectionId(Long sectionId);

    List<FacultyAssignment> findBySectionIdAndAcademicYear(Long sectionId, String academicYear);
}
