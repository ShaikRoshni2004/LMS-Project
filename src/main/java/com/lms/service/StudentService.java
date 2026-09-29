package com.lms.service;

import com.lms.model.Student;
import com.lms.repository.StudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "studentId"));
    }

    public List<Student> getFilteredStudents(String keyword, String status) {
        List<Student> students = studentRepository.findAll(Sort.by(Sort.Direction.DESC, "studentId"));

        return students.stream()
            .filter(student -> (keyword == null || keyword.isBlank()) ||
                student.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                student.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                student.getPhone().contains(keyword) ||
                String.valueOf(student.getStudentId()).contains(keyword))
            .filter(student -> (status == null || status.isBlank()) || (student.getStatus() != null && student.getStatus().equalsIgnoreCase(status)))
            .toList();
    }

    public Student saveStudent(Student student) {
        if (student.getStudentId() != null && emailExists(student.getEmail(), student.getStudentId())) {
            throw new IllegalArgumentException("A student with this email already exists.");
        }

        if (student.getStudentId() == null && emailExists(student.getEmail(), null)) {
            throw new IllegalArgumentException("A student with this email already exists.");
        }

        return studentRepository.save(student);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean emailExists(String email, Long excludeId) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Optional<Student> existing = studentRepository.findAll().stream()
            .filter(student -> student.getEmail().equalsIgnoreCase(email))
            .filter(student -> excludeId == null || !student.getStudentId().equals(excludeId))
            .findFirst();

        return existing.isPresent();
    }

    public long countByStatus(String status) {
        return studentRepository.countByStatusIgnoreCase(status);
    }
}
