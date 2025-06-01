package com.sipun.UniversityBackend.student.model;

import com.sipun.UniversityBackend.academic.model.*;
import com.sipun.UniversityBackend.attendance.model.AttendanceRecord;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.exam.model.ExamResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = "regdNo"),
        @UniqueConstraint(columnNames = "rollNo"),
        @UniqueConstraint(columnNames = "email")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 10)
    private String phone;

    private String emergencyContact; // Name + Phone

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Address is required")
    private String address;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Column(nullable = false, unique = true)
    private String regdNo; // Format: COURSE-YEAR-BRANCH-ROLL (e.g., BTECH2021CSE001)

    @Column(nullable = false, unique = true)
    private String rollNo; // Sequential within branch-batch (e.g., 210001)

    // Academic Relationships
    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "current_semester_id")
    private Semester currentSemester;


    // Authentication Link
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Audit Fields
    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    // Additional Fields
    private String bloodGroup;
    private String nationality;
    private String profilePhotoUrl; // URL to

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicStatus academicStatus; // e.g., ACTIVE, GRADUATED, SUSPENDED

    // Guardian Details (Embedded)
    @Embedded
    private Guardian guardian;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum AcademicStatus {
        ACTIVE, GRADUATED, SUSPENDED, ON_LEAVE, INACTIVE, // Profile incomplete
        PENDING_SECTION_ASSIGNMENT,
    }

    @OneToMany(mappedBy = "student")
    private List<AttendanceRecord> attendanceRecords;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ExamResult> examResults = new ArrayList<>();


    // Embeddable Guardian Class
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Guardian {
        @NotBlank
        private String guardianName;

        @Pattern(regexp = "^\\d{10}$")
        private String guardianPhone;

        private String relationship; // Father/Mother/Other
    }
}