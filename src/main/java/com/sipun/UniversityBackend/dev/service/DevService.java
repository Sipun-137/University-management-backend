package com.sipun.UniversityBackend.dev.service;

import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import com.sipun.UniversityBackend.student.dto.StudentRequestDTO;
import com.sipun.UniversityBackend.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevService {
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private SubjectRepo subjectRepository;

    @Autowired
    private StudentService service;

    public void updateTeachableSubjects(Long facultyId, List<Long> subjectIds) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        faculty.setTeachableSubjects(subjects);
        facultyRepository.save(faculty);
    }



    public String AddStudents(List<StudentRequestDTO> students){
        for(StudentRequestDTO student:students){
            service.createStudent(student);
        }
        return "success";
    }
}
