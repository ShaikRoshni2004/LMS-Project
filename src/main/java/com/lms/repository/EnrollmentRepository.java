package com.lms.repository;

import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentAndCourse(Student student, Course course);
    long countByStatusIgnoreCase(String status);
    long countByCourse(Course course);
    List<Enrollment> findTop5ByOrderByEnrollmentDateDesc();

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student s JOIN FETCH e.course c " +
            "WHERE (:studentId IS NULL OR s.studentId = :studentId) " +
            "AND (:courseId IS NULL OR c.courseId = :courseId) " +
            "AND (:status IS NULL OR :status = '' OR e.status = :status) " +
            "AND (:fromDate IS NULL OR e.enrollmentDate >= :fromDate) " +
            "AND (:toDate IS NULL OR e.enrollmentDate <= :toDate) " +
            "ORDER BY e.enrollmentDate DESC")
    List<Enrollment> findFiltered(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId,
            @Param("status") String status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
