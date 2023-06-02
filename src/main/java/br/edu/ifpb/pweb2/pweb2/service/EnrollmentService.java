package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.entity.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.entity.Enrollment;
import br.edu.ifpb.pweb2.pweb2.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;

    private StudentService studentService;

    private AcademicTermService academicTermService;

    public Enrollment createEnrollmentForStudent(Enrollment enrollment, Integer idStudent) {
        final var createdEnrollment = saveEnrollment(enrollment);
        studentService.addEnrollmentOntoStudent(createdEnrollment, idStudent);
        return createdEnrollment;
    }

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

    public void delete(Integer id) {
        final var enrollment = searchById(id);
        studentService.removeEnrollmentFromStudent(enrollment);
        enrollmentRepository.delete(enrollment);
    }

    public List<AcademicTerm> listAcademicTermsForStudent(Integer idStudent) {
        final var foundStudent = studentService.searchById(idStudent);
        return academicTermService.listAcademicTermsOfInstitution(foundStudent.getInstitution().getIdInstitution());
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public byte[] findEnrollmentPdfById(Integer id) {
        final var enrollment = searchById(id);
        return enrollment.getDocument();
    }
}
