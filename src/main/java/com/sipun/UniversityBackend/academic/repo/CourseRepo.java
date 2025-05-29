package com.sipun.UniversityBackend.academic.repo;

import com.sipun.UniversityBackend.academic.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {

}
