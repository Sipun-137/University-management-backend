package com.sipun.UniversityBackend.exam.service;

import com.sipun.UniversityBackend.academic.dto.BatchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SemesterMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.SubjectMinimalDTO;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.*;
import com.sipun.UniversityBackend.academic.repo.BatchRepo;
import com.sipun.UniversityBackend.academic.repo.BranchRepo;
import com.sipun.UniversityBackend.academic.repo.SemesterRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.exam.dto.ExamCreationDTO;
import com.sipun.UniversityBackend.exam.dto.ExamResponseDTO;
import com.sipun.UniversityBackend.exam.enums.ExamType;
import com.sipun.UniversityBackend.exam.model.Exam;
import com.sipun.UniversityBackend.exam.repo.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private SemesterRepo semesterRepo;
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private BatchRepo batchRepo;
    @Autowired
    private BranchRepo branchRepo;

    public ExamResponseDTO createExam(ExamCreationDTO exam) {

        boolean exist = examRepository.existsBySemesterIdAndBatchIdAndSubjectIdAndBranchIdAndExamType(exam.getSemesterId(), exam.getBatchId(), exam.getSubjectId(), exam.getBranchId(), ExamType.valueOf(exam.getExamType()));
        if (exist) {
            throw new IllegalStateException("Exam already exists");
        }
        // Validate exam data
        Semester semester = semesterRepo.findById(exam.getSemesterId()).orElseThrow(() -> new ResourceNotFoundException("Semester Not Found"));
        Subject subject = subjectRepo.findById(exam.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject Not Found"));
        Batch batch = batchRepo.findById(exam.getBatchId()).orElseThrow(() -> new ResourceNotFoundException("Batch Not Found"));
        Branch branch = branchRepo.findById(exam.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("Branch Not Found"));


        Exam examObj = Exam.builder()
                .batch(batch)
                .branch(branch)
                .semester(semester)
                .subject(subject)
                .examDate(exam.getExamDate())
                .durationMinutes(exam.getDurationMinutes())
                .examType(ExamType.valueOf(exam.getExamType()))
                .totalMarks(exam.getTotalMarks())
                .isPublished(false)
                .version(0)
                .build();
        Exam examResponse = examRepository.save(examObj);

        return ExamResponseDTO.builder()
                .id(examResponse.getId())
                .subject(new SubjectMinimalDTO(subject))
                .batch(new BatchMinimalDTO(batch))
                .branch(new BranchMinimalDTO(branch))
                .semester(new SemesterMinimalDTO(semester))
                .examType(examResponse.getExamType().name())
                .examDate(examResponse.getExamDate())
                .durationMinutes(examResponse.getDurationMinutes())
                .totalMarks(examResponse.getTotalMarks())
                .isPublished(examResponse.getIsPublished())
                .version(examResponse.getVersion())
                .build();
    }

    public List<Exam> getExamBySemesterBatch(Long semesterId, Long batchId) {
        return examRepository.findBySemesterIdAndBatchId(semesterId, batchId);
    }

    public Exam updateExam(Long id, Exam updatedExam) {
        Exam existing = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        existing.setSubject(updatedExam.getSubject());
        existing.setSemester(updatedExam.getSemester());
        existing.setBatch(updatedExam.getBatch());
        existing.setBranch(updatedExam.getBranch());
        existing.setExamType(updatedExam.getExamType());
        existing.setExamDate(updatedExam.getExamDate());
        existing.setTotalMarks(updatedExam.getTotalMarks());
        existing.setDurationMinutes(updatedExam.getDurationMinutes());
        existing.setIsPublished(updatedExam.getIsPublished());
        return examRepository.save(existing);
    }

    public void deleteExam(Long id) {
        boolean exists = examRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Exam not found");
        }
        examRepository.deleteById(id);
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
    }

    public List<ExamResponseDTO> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                .map(ExamResponseDTO::new)
                .toList();
    }

    public List<ExamResponseDTO> getExamByBatchAndBranchAndSemester(Long batchId, Long branchId, Long semesterId) {
        List<Exam> exams = examRepository.findByBatchIdAndBranchIdAndSemesterId(batchId, branchId, semesterId);
        return exams.stream()
                .map(ExamResponseDTO::new)
                .toList();
    }
}
