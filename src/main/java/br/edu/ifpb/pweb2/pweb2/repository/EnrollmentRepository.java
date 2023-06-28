package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findAllByStudent(Student student);

    List<Enrollment> findAllByAcademicTerm(AcademicTerm academicTerm);

    @Query("from Enrollment E WHERE E.expirationDate < CURRENT_DATE")
    List<Enrollment> getExpiredEnrollments();

    @Query("from Enrollment E WHERE E.expirationDate = :expiringDate")
    List<Enrollment> getEnrollmentsExpiringInDate(Date expiringDate);
}
