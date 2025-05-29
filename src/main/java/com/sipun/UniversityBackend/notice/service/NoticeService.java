package com.sipun.UniversityBackend.notice.service;

import com.sipun.UniversityBackend.notice.dto.NoticeRequestDTO;
import com.sipun.UniversityBackend.notice.dto.NoticeResponseDTO;
import com.sipun.UniversityBackend.notice.model.Notice;
import com.sipun.UniversityBackend.notice.repo.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public NoticeResponseDTO createNotice(NoticeRequestDTO dto) {
        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getDescription());
        notice.setType(dto.getType());
        notice.setTargetAudience(dto.getTargetAudience());
        notice.setValidFrom(dto.getValidFrom());
        notice.setValidTo(dto.getValidTo());
        notice.setCreatedAt(LocalDateTime.now());

        noticeRepository.save(notice);
        return mapToResponseDTO(notice);
    }

    public List<NoticeResponseDTO> getActiveNotices(String audience) {
        LocalDate today = LocalDate.now();
        List<Notice> notices = noticeRepository
                .findByTargetAudienceContainingAndValidToAfter(audience, today);
        return notices.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public List<NoticeResponseDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    private NoticeResponseDTO mapToResponseDTO(Notice notice) {
        NoticeResponseDTO dto = new NoticeResponseDTO();
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setDescription(notice.getContent());
//        dto.setAttachmentUrl(notice.getAttachmentUrl());
        dto.setType(notice.getType());
        dto.setTargetAudience(notice.getTargetAudience());
        dto.setValidFrom(notice.getValidFrom());
        dto.setValidTo(notice.getValidTo());
//        dto.setCreatedBy(notice.getCreatedBy());
        return dto;
    }
}
