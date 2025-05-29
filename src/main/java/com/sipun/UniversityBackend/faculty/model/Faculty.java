package com.sipun.UniversityBackend.faculty.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sipun.UniversityBackend.academic.model.Branch;
import com.sipun.UniversityBackend.academic.model.Subject;
import com.sipun.UniversityBackend.academic.model.TimeTableEntry;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.student.model.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculties", uniqueConstraints = @UniqueConstraint(
        columnNames = {"faculty_id", "section_id", "subject_id", "academicYear"}
))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank @Email
    @Column(unique = true)
    private String email;


    @NotBlank
    @Column(unique = true, nullable = false)
    private String employeeId;

    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Designation designation; // e.g., PROFESSOR, ASSOCIATE_PROFESSOR

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch department; // Faculty belongs to a department (CSE, ECE, etc.)

    @ManyToMany
    @JoinTable(
            name = "faculty_teachable_subjects",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @Builder.Default
    private List<Subject> teachableSubjects = new ArrayList<>();

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacultyAssignment> facultyAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference("faculty-timetable")
    private List<TimeTableEntry> timeTableEntries;


    private String address;
    private String bloodGroup;
    private String nationality;
    private String emergencyContact;
    private Student.Gender gender;
    private LocalDate dob;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private int maxLoadPerWeek=16;

    @CreationTimestamp
    private LocalDateTime createdAt;

//    @ManyToMany
//    @JoinTable(
//            name = "faculty_teachable_subjects",
//            joinColumns = @JoinColumn(name = "faculty_id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id")
//    )
//    @Builder.Default
//    private List<Subject> teachableSubjects = new ArrayList<>();

    public enum Designation {
        PROFESSOR, ASSOCIATE_PROFESSOR, ASSISTANT_PROFESSOR
    }
}