package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch,Long> {
}
