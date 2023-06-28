package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Page<Enrollment> findAllByStudent(Student student, Pageable pageable);

    List<Enrollment> findAllByAcademicTerm(AcademicTerm academicTerm);

    @Query("from Enrollment E WHERE E.expirationDate < CURRENT_DATE")
    Page<Enrollment> getExpiredEnrollments(Pageable pageable);

    @Query("from Enrollment E WHERE E.expirationDate = :expiringDate")
    Page<Enrollment> getEnrollmentsExpiringInDate(Date expiringDate, Pageable pageable);

    Page<Enrollment> findAll(Pageable pageable);
}
