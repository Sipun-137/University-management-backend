package com.sipun.UniversityBackend.exam.service;

import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.exam.dto.RubricCreateDTO;
import com.sipun.UniversityBackend.exam.dto.RubricResponseDTO;
import com.sipun.UniversityBackend.exam.model.Exam;
import com.sipun.UniversityBackend.exam.model.Rubric;
import com.sipun.UniversityBackend.exam.repo.ExamRepository;
import com.sipun.UniversityBackend.exam.repo.RubricRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RubricService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RubricRepo rubricRepo;

    @Autowired
    private UserRepo userRepo;

    //create a rubric
    public RubricResponseDTO createRubric(RubricCreateDTO rubric, Long examId, String userId) {

        User controller = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam Not Found"));

        Rubric newRubric = Rubric.builder().exam(exam).creator(controller).criteria(rubric.getCriteria()).status(rubric.getStatus()).maxMarks(rubric.getMaxMarks()).questionNumber(rubric.getQuestionNumber()).build();

        Rubric responseRubric = rubricRepo.save(newRubric);

        return new RubricResponseDTO(responseRubric);
    }

    //get all rubrics
    public List<RubricResponseDTO> getRubrics() {
        return rubricRepo.findAll().stream().map(RubricResponseDTO::new).toList();
    }

    //get rubric by id
    public RubricResponseDTO getRubric(Long id) {
        Rubric rubric = rubricRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rubric Not Found"));
        return new RubricResponseDTO(rubric);
    }

    //get Rubrics for a particular exam id
    public List<RubricResponseDTO> getRubricsByExamId(Long ExamId) {
        return rubricRepo.findByExam_Id(ExamId).stream().map(RubricResponseDTO::new).toList();
    }

    //delete a rubric by id
    public String deleteRubric(Long id) {
        Rubric rubric = rubricRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rubric Not Found"));
        rubricRepo.deleteById(id);
        return "deleted successfully";
    }

    //update a rubric
    public RubricResponseDTO updateRubric(Long id, RubricCreateDTO rubric) {
        Rubric existingRubric = rubricRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rubric Not Found"));

        existingRubric.setCriteria(rubric.getCriteria());
        existingRubric.setStatus(rubric.getStatus());
        existingRubric.setMaxMarks(rubric.getMaxMarks());
        existingRubric.setQuestionNumber(rubric.getQuestionNumber());
        return new RubricResponseDTO(rubricRepo.save(existingRubric));

    }


}
