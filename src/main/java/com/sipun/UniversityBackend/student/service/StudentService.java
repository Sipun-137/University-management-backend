package com.sipun.UniversityBackend.student.service;

import com.sipun.UniversityBackend.academic.dto.BatchMinimalDTO;
import com.sipun.UniversityBackend.academic.dto.BranchMinimalDTO;
import com.sipun.UniversityBackend.academic.exception.ConflictException;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.*;
import com.sipun.UniversityBackend.academic.repo.*;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.auth.service.MailService;
import com.sipun.UniversityBackend.student.dto.*;
import com.sipun.UniversityBackend.student.model.Student;
import com.sipun.UniversityBackend.student.repo.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private SemesterRepo semesterRepo;


    @Autowired
    private MailService emailService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public long getStudentIdFromUserId(String userId) {
        return studentRepo.findByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("Student not found")).getId();
    }

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        try {
            // Check if email is already registered
            if (studentRepo.existsByEmail(dto.getEmail())) {
                throw new ConflictException("Email already registered");
            }

            // Create User with temporary password
//            String tempPassword = generateTempPassword();
            String tempPassword = "test123";
            User user = User.builder()
                    .email(dto.getEmail())
                    .password(encoder.encode(tempPassword))
                    .role(User.Role.STUDENT)
                    .build();
            User savedUser = userRepo.save(user);


            int currentAcademicYear = getCurrentAcademicYear();

            Course course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            Branch branch = branchRepo.findById(dto.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

            Batch batch = batchRepo.findByCourseAndBranchAndStartYear(course, branch, currentAcademicYear)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No batch found for " + course.getName() + " - " + branch.getName() + " - " + currentAcademicYear
                    ));

            // Determine current academic year (e.g., 2023)
            int studentCount = studentRepo.countByBatchAndBranch(batch, branch);
            String regdNo = generateRegdNo(course, branch, batch, studentCount + 1);
            String rollNo = generateRollNo(branch, batch, studentCount + 1);

            List<Section> sections = sectionRepo.findByBatchAndBranch(batch, branch);
            Section section = sections.stream()
                    .filter(s -> s.getCurrentStrength() < s.getMaxStrength())
                    .min(Comparator.comparingInt(Section::getCurrentStrength))
                    .orElseThrow(() -> new ConflictException("No available sections in batch/branch"));

            // Update section's current strength
            section.setCurrentStrength(section.getCurrentStrength() + 1);
            sectionRepo.save(section);
            int currentSemesterNumber = 1;
            Semester semester = semesterRepo.findByBranchAndNumber(branch, currentSemesterNumber)
                    .orElseGet(() -> {
                        Integer maxSemester = semesterRepo.findMaxNumberByBranch(branch);
                        return semesterRepo.findByBranchAndNumber(branch, maxSemester)
                                .orElseThrow(() -> new ResourceNotFoundException("No semesters available"));
                    });

            // Create Student (INACTIVE status)
            Student student = Student.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .emergencyContact(dto.getEmergencyContact())
                    .gender(dto.getGender())
                    .address(dto.getAddress())
                    .dob(dto.getDob())
                    .regdNo(regdNo)
                    .rollNo(rollNo)
                    .batch(batch)
                    .section(section)
                    .currentSemester(semester)
                    .branch(branch)
                    .user(savedUser)
                    .academicStatus(Student.AcademicStatus.ACTIVE)
                    .bloodGroup(dto.getBloodGroup())
                    .nationality(dto.getNationality())
                    .guardian(Student.Guardian.builder()
                            .guardianName(dto.getGuardian().getGuardianName())
                            .guardianPhone(dto.getGuardian().getGuardianPhone())
                            .relationship(dto.getGuardian().getRelationship())
                            .build())
                    .build();

            Student savedStudent = studentRepo.save(student);

            // Send email with temporary password and profile link
            emailService.sendUserCredentials(savedStudent.getEmail(), tempPassword);
            return convertToMinimalResponseDTO(savedStudent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Admin assigns section and semester
    @Transactional
    public StudentResponseDTO assignSection(Long studentId, SectionAssignmentDTO dto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // Validate section belongs to the student's batch and branch
        Section section = sectionRepo.findById(dto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        if (!section.getBatch().equals(student.getBatch()) ||
                !section.getBranch().equals(student.getBranch())) {
            throw new ConflictException("Section does not match student's batch/branch");
        }

        // Validate semester
        Semester semester = semesterRepo.findById(dto.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));

        // Update student
        student.setSection(section);
        student.setCurrentSemester(semester);
        student.setAcademicStatus(Student.AcademicStatus.ACTIVE);

        Student updated = studentRepo.save(student);
        return convertToStudentResponseDTO(updated);
    }

    // --- Helper Methods ---

    private String generateRegdNo(Course course, Branch branch, Batch batch, int sequence) {
        String courseAbbreviation = course.getCode(); // e.g., "BTECH"
        String batchYear = String.valueOf(batch.getStartYear()).substring(2); // "2023" → "23"
        String branchCode = branch.getCode(); // e.g., "CSE"
        return String.format("%s%s%s%03d", courseAbbreviation, batchYear, branchCode, sequence);
    }

    private String generateRollNo(Branch branch, Batch batch, int sequence) {
        String batchYear = String.valueOf(batch.getStartYear()).substring(2); // "2023" → "23"
        String branchCode = branch.getCode(); // e.g., "CSE"
        return String.format("%s%s%03d", batchYear, branchCode, sequence);
    }

    private int getCurrentAcademicYear() {
        // Example: Academic year starts in August.
        // If current month is >= August, return current year; else, previous year.
        LocalDate today = LocalDate.now();
        return (today.getMonthValue() >= 8) ? today.getYear() : today.getYear() - 1;
    }

    private String generateTempPassword() {
        // Generate a random 8-character password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(new Random().nextInt(chars.length())));
        }
        return sb.toString();
    }

    // --- DTO Conversions ---

    private StudentResponseDTO convertToMinimalResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .email(student.getEmail())
                .academicStatus(student.getAcademicStatus().name())
                .build();
    }

    private StudentResponseDTO convertToStudentResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .dob(student.getDob())
                .address(student.getAddress())
                .regdNo(student.getRegdNo())
                .rollNo(student.getRollNo())
                .course(student.getBranch().getCourse().getName())
                .branch(new BranchMinimalDTO(student.getBranch()))
                .batch(new BatchMinimalDTO(student.getBatch()))
                .section(student.getSection() != null ? student.getSection().getName() : "Not assigned")
                .currentSemester(student.getCurrentSemester() != null ?
                        "Semester " + student.getCurrentSemester().getNumber() : "Not assigned")
                .academicStatus(student.getAcademicStatus().name())
                .guardian(new GuardianDTO(student.getGuardian().getGuardianName(), student.getGuardian().getGuardianPhone(), student.getGuardian().getRelationship()))
                .nationality(student.getNationality())
                .emergencyContact(student.getEmergencyContact())
                .bloodGroup(student.getBloodGroup())
                .gender(student.getGender().name())
                .profilePhotoUrl(student.getProfilePhotoUrl())
                .build();
    }


    public List<StudentResponseDTO> findAll() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(this::convertToStudentResponseDTO)
                .toList();

    }

    public StudentResponseDTO findById(Long id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        return convertToStudentResponseDTO(student);
    }

    public List<StudentMinimalDTO> getStudentsBySemesterIdAndSectionId(Long semesterId, Long sectionId) {
        List<Student> students = studentRepo.findBySectionIdAndCurrentSemester_Id(sectionId, semesterId);
        return students.stream().map(StudentMinimalDTO::new).toList();
    }
}