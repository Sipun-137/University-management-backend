package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepo extends JpaRepository<Batch,Long> {

    @EntityGraph(attributePaths = {"course", "sections"})
    List<Batch> findAll();

    Optional<Batch> findByCourseAndStartYear(Course course, int startYear);

    @Query("SELECT b FROM Batch b " +
            "JOIN b.sections s " +
            "WHERE b.course = :course AND s.branch = :branch AND b.startYear = :startYear")
    Optional<Batch> findByCourseAndBranchAndStartYear(
            @Param("course") Course course,
            @Param("branch") Branch branch,
            @Param("startYear") int startYear
    );

}
