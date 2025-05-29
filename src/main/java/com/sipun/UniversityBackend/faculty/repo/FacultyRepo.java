package com.sipun.UniversityBackend.faculty.repo;


import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepo extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByEmail(String email);
    List<Faculty> findByDepartment_Id(Long branchId);
    boolean existsByEmail(String email);
    boolean existsByEmployeeId(String employeeId);

    int countByDepartment(Branch department );

    @Query("SELECT f FROM Faculty f LEFT JOIN FETCH f.teachableSubjects")
    List<Faculty> findAllWithTeachableSubjects();

    Optional<Faculty> findByUserId(String userId);
}
