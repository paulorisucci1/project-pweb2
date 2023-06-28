package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByRegistration(String registration);

    boolean existsByRegistrationAndIdStudentNot(String registration, Integer idStudent);

    @Query("FROM Student WHERE currentEnrollment IS NULL")
    List<Student> findAllWithoutEnrollment();
}
