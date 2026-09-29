package com.lms.service;

import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        if (enrollment.getStudent() == null || enrollment.getCourse() == null) {
            throw new IllegalArgumentException("Student and course are required.");
        }

        if (enrollmentRepository.existsByStudentAndCourse(enrollment.getStudent(), enrollment.getCourse())) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }

        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        enrollments.sort(Comparator.comparing(Enrollment::getEnrollmentDate).reversed());
        return enrollments;
    }

    public List<Enrollment> getFilteredEnrollments(Long studentId, Long courseId, String status, LocalDate fromDate, LocalDate toDate) {
        return enrollmentRepository.findFiltered(studentId, courseId, status, fromDate, toDate);
    }

    public List<Enrollment> getRecentEnrollments(int limit) {
        List<Enrollment> enrollments = enrollmentRepository.findTop5ByOrderByEnrollmentDateDesc();
        return enrollments.size() > limit ? enrollments.subList(0, limit) : enrollments;
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public boolean existsByStudentAndCourse(Student student, Course course) {
        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }

    public List<Map<String, Object>> getCourseEnrollmentStats() {
        List<Map<String, Object>> stats = new ArrayList<>();

        for (Course course : courseRepository.findAll()) {
            Map<String, Object> row = new HashMap<>();
            row.put("courseName", course.getCourseName());
            row.put("count", enrollmentRepository.countByCourse(course));
            stats.add(row);
        }

        stats.sort((a, b) -> Long.compare(((Number) b.get("count")).longValue(), ((Number) a.get("count")).longValue()));
        return stats;
    }

    public long countByStatus(String status) {
        return enrollmentRepository.countByStatusIgnoreCase(status);
    }
}
