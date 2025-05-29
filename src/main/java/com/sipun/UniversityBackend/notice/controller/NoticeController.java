package com.sipun.UniversityBackend.notice.controller;


import com.sipun.UniversityBackend.notice.dto.NoticeRequestDTO;
import com.sipun.UniversityBackend.notice.dto.NoticeResponseDTO;
import com.sipun.UniversityBackend.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NoticeResponseDTO> createNotice(@RequestBody NoticeRequestDTO dto) {
        NoticeResponseDTO created = noticeService.createNotice(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponseDTO>> getNotices(
            @RequestParam String audience) {
        return ResponseEntity.ok(noticeService.getActiveNotices(audience));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoticeResponseDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
