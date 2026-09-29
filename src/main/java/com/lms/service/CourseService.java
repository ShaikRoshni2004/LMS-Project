package com.lms.service;

import com.lms.model.Course;
import com.lms.repository.CourseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll(Sort.by(Sort.Direction.DESC, "courseId"));
    }

    public List<Course> getFilteredCourses(String keyword, String status) {
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.DESC, "courseId"));

        return courses.stream()
            .filter(course -> (keyword == null || keyword.isBlank()) ||
                course.getCourseName().toLowerCase().contains(keyword.toLowerCase()) ||
                course.getCourseCode().toLowerCase().contains(keyword.toLowerCase()) ||
                course.getInstructorName().toLowerCase().contains(keyword.toLowerCase()))
            .filter(course -> (status == null || status.isBlank()) ||
                (course.getStatus() != null && course.getStatus().equalsIgnoreCase(status)))
            .toList();
    }

    public Course saveCourse(Course course) {
        if (course.getCourseId() != null && courseRepository.existsByCourseCode(course.getCourseCode()) &&
            courseRepository.findById(course.getCourseId()).map(existing -> !existing.getCourseCode().equalsIgnoreCase(course.getCourseCode())).orElse(false)) {
            throw new IllegalArgumentException("Course code already exists.");
        }

        if (course.getCourseId() == null && courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new IllegalArgumentException("Course code already exists.");
        }

        return courseRepository.save(course);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public long countByStatus(String status) {
        return courseRepository.countByStatusIgnoreCase(status);
    }
}
