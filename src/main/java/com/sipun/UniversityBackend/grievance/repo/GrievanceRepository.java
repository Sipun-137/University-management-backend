package com.sipun.UniversityBackend.grievance.repo;

import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.grievance.model.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    List<Grievance> findBySubmittedBy(User user);
}
