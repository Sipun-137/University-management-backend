package com.sipun.UniversityBackend.exam.repo;

import com.sipun.UniversityBackend.exam.model.MarkingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkingAssignmentRepo extends JpaRepository<MarkingAssignment, Long> {

    List<MarkingAssignment> findByExam_Id(Long examId);
    List<MarkingAssignment> findByMarker_Id(Long markerId);
    List<MarkingAssignment> findByMarker_Faculty_User_Id(String userId);
}
