package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject,Long> {
    boolean existsBySubjectCode(String subjectCode);

    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.semester")
    List<Subject> findAllWithSemester();

    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.semester WHERE s.id = :id")
    Optional<Subject> findByIdWithSemester(@Param("id") Long id);

    @Query("SELECT s FROM Subject s " +
            "LEFT JOIN FETCH s.semester sem " +
            "LEFT JOIN FETCH sem.branch br " +
            "LEFT JOIN FETCH br.course " )
    List<Subject> findWithDetails();

    List<Subject> findBySemesterNumber(Long semesterNumber);
}
