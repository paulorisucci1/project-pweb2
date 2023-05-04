package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;

    private StudentService studentService;

    private AcademicTermService academicTermService;

    @Transactional
    public Enrollment createEnrollmentForStudent(Enrollment enrollment, Integer idStudent) {
        final var createdEnrollment = saveEnrollment(enrollment);
        studentService.addEnrollmentOntoStudent(createdEnrollment, idStudent);
        return createdEnrollment;
    }

    @Transactional
    public Enrollment update(Enrollment updatedEnrollment) {
        final var foundEnrollment = searchById(updatedEnrollment.getIdEnrollment());
        foundEnrollment.update(updatedEnrollment);
        return saveEnrollment(foundEnrollment);
    }

    public List<Enrollment> listEnrollmentsOfStudent(Integer idStudent) {
        final var institution = studentService.searchById(idStudent);
        return enrollmentRepository.findAllByStudent(institution);
    }

    public Enrollment searchById(Integer id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));
    }

    @Transactional
    public void delete(Integer id) {
        final var enrollment = searchById(id);
        studentService.removeEnrollmentFromStudent(enrollment);
        enrollmentRepository.delete(enrollment);
    }

    public List<AcademicTerm> listAcademicTermsForStudent(Integer idStudent) {
        final var foundStudent = studentService.searchById(idStudent);
        return academicTermService.listAcademicTermsOfInstitution(foundStudent.getInstitution().getIdInstitution());
    }

    private Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
