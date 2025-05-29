package com.sipun.UniversityBackend.auth.service;

import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.student.model.Student;
import com.sipun.UniversityBackend.student.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final StudentRepo studentRepo;

    @Autowired
    public SecurityService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    // Check if the logged-in user owns the student profile
    public boolean isStudentOwner(User user, Long studentId) {
        if (user == null || studentId == null) return false;

        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return student.getId().equals(studentId);
    }
}