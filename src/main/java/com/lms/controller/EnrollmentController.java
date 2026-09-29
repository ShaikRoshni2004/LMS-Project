package com.lms.controller;

import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
import com.lms.service.CourseService;
import com.lms.service.EnrollmentService;
import com.lms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                               StudentService studentService,
                               CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listEnrollments(@RequestParam(value = "studentId", required = false) Long studentId,
                                 @RequestParam(value = "courseId", required = false) Long courseId,
                                 @RequestParam(value = "status", required = false) String status,
                                 Model model) {
        List<Enrollment> enrollments = enrollmentService.getFilteredEnrollments(studentId, courseId, status, null, null);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("selectedStudentId", studentId);
        model.addAttribute("selectedCourseId", courseId);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("pageTitle", "Enrollments");
        return "enrollments/enrollments";
    }

    @GetMapping("/add")
    public String addEnrollmentForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("pageTitle", "New Enrollment");
        return "enrollments/add-enrollment";
    }

    @PostMapping("/save")
    public String saveEnrollment(@Valid @ModelAttribute("enrollment") Enrollment enrollment,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("pageTitle", "New Enrollment");
            return "enrollments/add-enrollment";
        }

        try {
            Student student = studentService.findById(enrollment.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found."));
            Course course = courseService.findById(enrollment.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

            enrollment.setStudent(student);
            enrollment.setCourse(course);
            if (enrollment.getEnrollmentDate() == null) {
                enrollment.setEnrollmentDate(LocalDate.now());
            }
            enrollmentService.saveEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment saved successfully.");
            return "redirect:/enrollments";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("pageTitle", "New Enrollment");
            return "enrollments/add-enrollment";
        }
    }

    @GetMapping("/edit/{id}")
    public String editEnrollmentForm(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getAllEnrollments().stream()
            .filter(item -> item.getEnrollmentId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("pageTitle", "Edit Enrollment");
        return "enrollments/edit-enrollment";
    }

    @PostMapping("/update/{id}")
    public String updateEnrollment(@PathVariable Long id,
                                   @Valid @ModelAttribute("enrollment") Enrollment enrollment,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("pageTitle", "Edit Enrollment");
            return "enrollments/edit-enrollment";
        }

        try {
            Student student = studentService.findById(enrollment.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found."));
            Course course = courseService.findById(enrollment.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

            enrollment.setEnrollmentId(id);
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentService.saveEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment updated successfully.");
            return "redirect:/enrollments";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("pageTitle", "Edit Enrollment");
            return "enrollments/edit-enrollment";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enrollmentService.deleteEnrollment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Enrollment removed successfully.");
        return "redirect:/enrollments";
    }
}
