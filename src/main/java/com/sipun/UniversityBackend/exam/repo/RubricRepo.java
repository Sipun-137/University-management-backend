package com.sipun.UniversityBackend.exam.repo;

import com.sipun.UniversityBackend.exam.model.Rubric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RubricRepo extends JpaRepository<Rubric, Long> {
    List<Rubric> findByExam_Id(Long id);
}
