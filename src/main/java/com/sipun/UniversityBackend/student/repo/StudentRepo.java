package com.sipun.UniversityBackend.student.repo;

import com.sipun.UniversityBackend.academic.model.Batch;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {

    boolean existsByEmail(String email);

    int countByBatchAndBranch(Batch batch, Branch branch);

    Optional<Student> findByUser(User user);

    List<Student> findBySectionIdAndCurrentSemester_Id(Long sectionId, Long semesterId);

    Optional<Student> findByUser_Id(String user_id);

    String user(User user);
}
