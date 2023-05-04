package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByNameAndIdStudentNot(String name, Integer idStudent);
}
