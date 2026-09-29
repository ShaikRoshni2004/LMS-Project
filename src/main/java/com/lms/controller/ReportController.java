package com.lms.controller;

import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.StudentRepository;
import com.lms.service.CourseService;
import com.lms.service.EnrollmentService;
import com.lms.service.StudentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public ReportController(StudentService studentService,
                           CourseService courseService,
                           EnrollmentService enrollmentService,
                           StudentRepository studentRepository,
                           CourseRepository courseRepository,
                           EnrollmentRepository enrollmentRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/reports")
    public String reports(@RequestParam(value = "courseId", required = false) Long courseId,
                          @RequestParam(value = "status", required = false) String status,
                          @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                          @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                          Model model) {
        List<Student> students = studentService.getAllStudents();
        List<Course> courses = courseService.getAllCourses();
        List<Enrollment> enrollments = enrollmentService.getFilteredEnrollments(null, courseId, status, fromDate, toDate);

        long activeStudents = studentRepository.countByStatusIgnoreCase("Active");
        long activeCourses = courseRepository.countByStatusIgnoreCase("Active");

        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalCourses", courseRepository.count());
        model.addAttribute("totalEnrollments", enrollmentRepository.count());
        model.addAttribute("activeStudents", activeStudents);
        model.addAttribute("activeCourses", activeCourses);
        model.addAttribute("selectedCourseId", courseId);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("pageTitle", "Reports");
        return "reports/reports";
    }

    @GetMapping("/reports/export/csv")
    public ResponseEntity<ByteArrayResource> exportCsv(
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        List<Enrollment> enrollments = enrollmentService.getFilteredEnrollments(null, courseId, status, fromDate, toDate);
        StringBuilder csv = new StringBuilder();
        csv.append("Enrollment ID,Student,Course,Enrollment Date,Status\n");

        for (Enrollment enrollment : enrollments) {
            csv.append(enrollment.getEnrollmentId())
                .append(',')
                .append(enrollment.getStudent().getFullName())
                .append(',')
                .append(enrollment.getCourse().getCourseName())
                .append(',')
                .append(enrollment.getEnrollmentDate())
                .append(',')
                .append(enrollment.getStatus())
                .append('\n');
        }

        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(bytes);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lms_enrollment_report.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .contentLength(bytes.length)
            .body(resource);
    }
}
