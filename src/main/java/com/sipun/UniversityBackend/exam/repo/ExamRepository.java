package com.sipun.UniversityBackend.exam.repo;

import com.sipun.UniversityBackend.exam.enums.ExamType;
import com.sipun.UniversityBackend.exam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findBySemesterIdAndBatchId(Long semesterId, Long batchId);

    List<Exam> findByBranchIdAndBatchId(Long branchId, Long batchId);

    List<Exam> findBySubjectId(Long subjectId);


    boolean existsBySemesterIdAndBatchIdAndSubjectIdAndBranchIdAndExamType(Long semesterId, Long batchId, Long subjectId, Long branchId, ExamType examType);

    List<Exam> findByBatchIdAndBranchIdAndSemesterId(Long batchId, Long branchId, Long semesterId);

}
