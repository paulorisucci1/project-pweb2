package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByRegistration(String registration);

    boolean existsByRegistrationAndIdStudentNot(String registration, Integer idStudent);

    Page<Student> findAllByCurrentEnrollmentIsNull(Pageable pageable);

    Page<Student> findAll(Pageable pageable);

}
