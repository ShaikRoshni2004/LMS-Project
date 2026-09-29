package com.lms.controller;

import com.lms.model.Enrollment;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.StudentRepository;
import com.lms.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;

    public DashboardController(StudentRepository studentRepository,
                              CourseRepository courseRepository,
                              EnrollmentRepository enrollmentRepository,
                              EnrollmentService enrollmentService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();
        long totalEnrollments = enrollmentRepository.count();
        long activeCourses = courseRepository.countByStatusIgnoreCase("Active");
        List<Enrollment> recentEnrollments = enrollmentService.getRecentEnrollments(5);

        List<Map<String, Object>> courseStats = enrollmentService.getCourseEnrollmentStats();
        List<String> courseLabels = new ArrayList<>();
        List<Long> courseValues = new ArrayList<>();

        for (Map<String, Object> stat : courseStats) {
            courseLabels.add((String) stat.get("courseName"));
            courseValues.add(((Number) stat.get("count")).longValue());
        }

        long enrolledCount = enrollmentRepository.countByStatusIgnoreCase("Enrolled");
        long completedCount = enrollmentRepository.countByStatusIgnoreCase("Completed");
        long cancelledCount = enrollmentRepository.countByStatusIgnoreCase("Cancelled");

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalEnrollments", totalEnrollments);
        model.addAttribute("activeCourses", activeCourses);
        model.addAttribute("recentEnrollments", recentEnrollments);
        model.addAttribute("courseLabels", courseLabels);
        model.addAttribute("courseValues", courseValues);
        model.addAttribute("enrolledCount", enrolledCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("pageTitle", "Dashboard");
        return "dashboard";
    }
}
