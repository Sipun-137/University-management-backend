package com.sipun.UniversityBackend.exam.repo;

import com.sipun.UniversityBackend.exam.model.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepo extends JpaRepository<Marker, Long> {
}
