package com.sipun.UniversityBackend.exam.repo;

import com.sipun.UniversityBackend.exam.model.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepo extends JpaRepository<Marker, Long> {
}
