package com.lms.repository;

import com.lms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStatusIgnoreCase(String status);
    List<Student> findByFullNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
    long countByStatusIgnoreCase(String status);
}
