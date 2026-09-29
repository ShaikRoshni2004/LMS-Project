package com.lms.controller;

import com.lms.model.Student;
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

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(@RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "status", required = false) String status,
                              Model model) {
        model.addAttribute("students", studentService.getFilteredStudents(query, status));
        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("pageTitle", "Students");
        return "students/students";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add Student");
        return "students/add-student";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Student");
            return "students/add-student";
        }

        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student saved successfully.");
            return "redirect:/students";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Add Student");
            return "students/add-student";
        }
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");
        return "students/edit-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                               @Valid @ModelAttribute("student") Student student,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Student");
            return "students/edit-student";
        }

        try {
            student.setStudentId(id);
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully.");
            return "redirect:/students";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Edit Student");
            return "students/edit-student";
        }
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Student Details");
        return "students/view-student";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students";
    }
}
