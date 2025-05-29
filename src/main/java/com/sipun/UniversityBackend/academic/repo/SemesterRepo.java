package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepo  extends JpaRepository<Semester,Long>
{
    @Query("SELECT s FROM Semester s LEFT JOIN FETCH s.subjects WHERE s.id = :id")
    Optional<Semester> findByIdWithSubjects(@Param("id") Long id);

    // Fetch all semesters with subjects
    @Query("SELECT DISTINCT s FROM Semester s LEFT JOIN FETCH s.subjects")
    List<Semester> findAllWithSubjects();

    List<Semester> findByBranchIdAndIsCurrentTrue(Long branchId);

    @Query("SELECT MAX(s.number) FROM Semester s WHERE s.branch = :branch")
    Integer findMaxNumberByBranch(Branch branch);

    Optional<Semester> findByBranchAndNumber(Branch branch, Integer number);
}
