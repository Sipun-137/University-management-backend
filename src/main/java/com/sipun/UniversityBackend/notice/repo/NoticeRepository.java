package com.sipun.UniversityBackend.notice.repo;

import com.sipun.UniversityBackend.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTargetAudienceContainingAndValidToAfter(String audience, LocalDate date);
}
