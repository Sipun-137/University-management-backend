package com.sipun.UniversityBackend.faculty.service;

import com.sipun.UniversityBackend.academic.exception.ConflictException;
import com.sipun.UniversityBackend.academic.exception.ResourceNotFoundException;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Section;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.repo.BranchRepo;
import com.sipun.UniversityBackend.academic.repo.SectionRepo;
import com.sipun.UniversityBackend.academic.repo.SubjectRepo;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.auth.service.MailService;
import com.sipun.UniversityBackend.exception.DuplicateResourceException;
import com.sipun.UniversityBackend.faculty.dto.CreateFacultyDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyAssignmentDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyAssignmentResponseDTO;
import com.sipun.UniversityBackend.faculty.dto.FacultyResponseDTO;
import com.sipun.UniversityBackend.faculty.model.Faculty;
import com.sipun.UniversityBackend.faculty.model.FacultyAssignment;
import com.sipun.UniversityBackend.faculty.repo.FacultyAssignmentRepository;
import com.sipun.UniversityBackend.faculty.repo.FacultyRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

// FacultyService.java
@Service
@Transactional
public class FacultyService {

    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private FacultyAssignmentRepository assignmentRepository;

    @Autowired
    private BranchRepo branchRepository;

    @Autowired
    private SectionRepo sectionRepository;

    @Autowired
    private SubjectRepo subjectRepository;

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private MailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);



    public FacultyResponseDTO createFaculty(CreateFacultyDTO dto) {
        try {

            if (facultyRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateResourceException("Email already registered");
            }

            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

//            String tempPassword = generateTempPassword();
            String tempPassword="test123";
            int sequence = facultyRepository.countByDepartment(branch)+1;
            String employeeId = generateemployeeId(branch, sequence);
            User newUser = User.builder()
                    .email(dto.getEmail())
                    .password(encoder.encode(tempPassword))
                    .role(User.Role.FACULTY)
                    .build();
            User savedUser = userrepo.save(newUser);
            Faculty faculty = Faculty.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .address(dto.getAddress())
                    .gender(dto.getGender())
                    .employeeId(employeeId)
                    .dob(dto.getDob())
                    .phone(dto.getPhone())
                    .designation(Faculty.Designation.ASSOCIATE_PROFESSOR)
                    .bloodGroup(dto.getBloodGroup())
                    .emergencyContact(dto.getEmergencyContact())
                    .nationality(dto.getNationality())
                    .department(branch)
                    .user(savedUser)
                    .createdAt(LocalDateTime.now())
                    .build();
            Faculty savedFaculty=facultyRepository.save(faculty);
            emailService.sendUserCredentials(savedFaculty.getEmail(), tempPassword);
            return convertToDTO(savedFaculty);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<FacultyResponseDTO> findAll(){
        List<Faculty> faculties=facultyRepository.findAll();
        return faculties.stream()
                .map(FacultyResponseDTO::new)
                .toList();
    }


    public List<FacultyAssignmentResponseDTO> getAllAssignments(){
        List<FacultyAssignment> assignments=assignmentRepository.findAll();
        return assignments.stream()
                .map(FacultyAssignmentResponseDTO::new)
                .toList();
    }

//    public FacultyResponseDTO updateFaculty(Long id, UpdateFacultyDTO dto) {
//        Faculty faculty = facultyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
//
//        if (dto.firstName() != null) faculty.setFirstName(dto.firstName());
//        if (dto.lastName() != null) faculty.setLastName(dto.lastName());
//        if (dto.email() != null && !dto.email().equals(faculty.getEmail())) {
//            if (facultyRepository.existsByEmail(dto.email())) {
//                throw new DuplicateResourceException("Email already in use");
//            }
//            faculty.setEmail(dto.email());
//        }
//        if (dto.branchId() != null) {
//            Branch branch = branchRepository.findById(dto.branchId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
//            faculty.setBranch(branch);
//        }
//        faculty.setActive(dto.active());
//        faculty.setUpdatedAt(LocalDateTime.now());
//
//        return convertToDTO(facultyRepository.save(faculty));
//    }

    public FacultyResponseDTO addAssignment(Long facultyId, FacultyAssignmentDTO dto) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        Section section = sectionRepository.findById(dto.sectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        Subject subject = subjectRepository.findById(dto.subjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if (assignmentRepository.existsByFacultyAndSectionAndSubjectAndAcademicYear(
                faculty, section, subject, dto.academicYear())) {
            throw new ConflictException("Assignment already exists");
        }

        FacultyAssignment assignment = FacultyAssignment.builder()
                .faculty(faculty)
                .section(section)
                .subject(subject)
                .academicYear(dto.academicYear())
                .term(dto.term())
                .weeklyHours(dto.weeklyHours())
                .build();

//        faculty.getAssignments().add(assignment);
        facultyRepository.save(faculty);

        return convertToDTO(faculty);
    }

    private FacultyResponseDTO convertToDTO(Faculty faculty) {
        return FacultyResponseDTO.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .email(faculty.getEmail())
                .employeeId(faculty.getEmployeeId())
                .build();
    }

//    private FacultyAssignmentDTO convertAssignmentToDTO(FacultyAssignment assignment) {
//        return new FacultyAssignmentDTO(
//                assignment.getSection().getId(),
//                assignment.getSection().getName(),
//                assignment.getSubject().getId(),
//                assignment.getSubject().getSubjectCode(),
//                assignment.getAcademicYear(),
//                assignment.getTerm(),
//                assignment.getWeeklyHours()
//        );
//    }

    public String autoCreateFaculty(List<CreateFacultyDTO> faculties){
        for(CreateFacultyDTO faculty :faculties){
            createFaculty(faculty);
        }
        return "success";
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


    private String generateemployeeId(Branch branch, int sequence) {
        String branchCode = branch.getCode();
        return String.format("%s%03d", branchCode, sequence);
    }
}
