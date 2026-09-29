package com.lms.controller;

import com.lms.model.Course;
import com.lms.service.CourseService;
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
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(@RequestParam(value = "query", required = false) String query,
                             @RequestParam(value = "status", required = false) String status,
                             Model model) {
        model.addAttribute("courses", courseService.getFilteredCourses(query, status));
        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("pageTitle", "Courses");
        return "courses/courses";
    }

    @GetMapping("/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Add Course");
        return "courses/add-course";
    }

    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Course");
            return "courses/add-course";
        }

        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course saved successfully.");
            return "redirect:/courses";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Add Course");
            return "courses/add-course";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("pageTitle", "Edit Course");
        return "courses/edit-course";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("course") Course course,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/edit-course";
        }

        try {
            course.setCourseId(id);
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully.");
            return "redirect:/courses";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/edit-course";
        }
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("pageTitle", "Course Details");
        return "courses/view-course";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully.");
        return "redirect:/courses";
    }
}
