package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.entity.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.entity.Enrollment;
import br.edu.ifpb.pweb2.pweb2.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findAllByStudent(Student student);

    List<Enrollment> findAllByAcademicTerm(AcademicTerm academicTerm);
}
