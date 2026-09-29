package com.lms.repository;

import com.lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseCode(String courseCode);
    List<Course> findByStatusIgnoreCase(String status);
    long countByStatusIgnoreCase(String status);
    List<Course> findByCourseNameContainingIgnoreCase(String keyword);
}
